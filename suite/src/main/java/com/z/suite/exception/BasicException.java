package com.z.suite.exception;

/**
 * @author zcy
 * @create 2020-03-22 13:52:37
 */
public class BasicException extends Exception {
    // 错误编码
    private int errorCode;

    public BasicException(int errorCode) {
        super("ERROR");
        this.errorCode = errorCode;
    }

    public BasicException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
