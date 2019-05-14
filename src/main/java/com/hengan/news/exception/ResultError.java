package com.hengan.news.exception;

import java.io.Serializable;

/**
 * @Author Cyq
 * @Date 2019/4/28 15:09
 **/
public class ResultError implements Serializable {

    /**状态码*/
    protected Integer code = 200;
    /**描述信息*/
    protected String message = "success";
    /**错误ID*/
    private String errorId;
    /**错误编码*/
    private String errorCode;
    /**错误详情*/
    private String detail;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
