package com.z.suite.exception;

public class SystemException extends BasicException {

    public SystemException(int errorCode) {
        super(errorCode, "系统繁忙，请稍后再试");
    }

    public SystemException(int errorCode, String msg) {
        super(errorCode, msg);
    }

}
