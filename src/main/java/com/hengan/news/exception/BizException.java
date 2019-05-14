package com.hengan.news.exception;


public class BizException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**异常编码*/
	private String errorCode = "CEC-000001";
	/**异常描述*/
	private String errorMessage = "UNDEFINED";
	/**国际化中的Key，去掉 .CODE  .MESSAGE*/
	private String messageKey ;

	
	public BizException() {

	}

	/**
	 * 使用国际化来初始化错误信息
	 * @param messageKey * 国际化中的KEY，去掉 .CODE  .MESSAGE
	 */
	public BizException(String messageKey) {
		this.messageKey = messageKey;
	}

	public BizException(String code, String message) {
		this.errorCode = code;
		this.errorMessage = message;
	}

	public BizException(String code, String message, Throwable cause) {
		super(cause);
		this.errorCode = code;
		this.errorMessage = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
}
