package com.zjh.backend.exception;
/**
 * 自定义warning！捕获warning防止throw出错导致 程序终止？
 * */
public class BIException extends RuntimeException {
    private final Integer code;
    private final String description;

    public BIException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }
    public BIException(ErrorCode errorCode, String description){
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
