package com.hengan.news.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * 异常处理类
 * 
 * @since
 *
 */
@ControllerAdvice
public class BizExceptionHandler{

	@Resource
	private MessageSource messageSource;
	
	/**
	 * 添加全局异常处理流程，根据需要设置需要处理的异常，本文以MethodArgumentNotValidException为例
	 * @param request
	 * @param ex
	 * @return
	 * @throws Exception
	 */
    @ExceptionHandler(value=BizException.class)
    @ResponseBody
    public ResultError bizExceptionHandler(HttpServletRequest request, BizException ex) throws Exception
    {
		ResultError resultError = new ResultError();
		resultError.setCode(500);
		if(ex instanceof  BizException){
			BizException exception=(BizException) ex;
			if(StringUtils.isNotBlank(exception.getMessageKey())){
				Locale locale = LocaleContextHolder.getLocale();
				resultError.setErrorCode(messageSource.getMessage(exception.getMessageKey()+".CODE",null,locale));
				resultError.setMessage(messageSource.getMessage(exception.getMessageKey()+".MESSAGE",null,locale));
			}else {
				resultError.setErrorCode(exception.getErrorCode());
				resultError.setMessage(exception.getErrorMessage());
			}
		}else{
			resultError.setErrorCode("CEC-000001");
		}
		resultError.setDetail(getExceptionDetail(ex));
		return resultError;
    }  

	/**
	 * 获取异常堆栈信息
	 *
	 * @param ex
	 * @return
	 */
	public String getExceptionDetail(Throwable ex) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		ex.printStackTrace(printWriter);
		return result.toString();
	}

}
