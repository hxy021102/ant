package com.mobian.exception;

/**
 * Created by john on 17/5/14.
 * 业务异常
 */
public class ServiceException extends RuntimeException {
    private String msg;


    public ServiceException(String message) {
        super(message);
        this.msg = message;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
