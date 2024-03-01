package com.zjh.backend.exception;

public enum ErrorCode implements ErrorDescription{

    // 元组内创建初始化
    // 1. 参数错误
    PARAM_ERROR(40000, ERROR, PARAM_DESCRIPTION),
    // 2. 求其数据为空
    NO_ASK_PARAM_ERROR(40001, ERROR, NULL_DESCRIPTION),
    // 3. 未登录错误
    NO_LOGIN_ERROR(40100, ERROR, NOT_LOGIN_DESCRIPTION),
    // 4. 权限错误
    NO_AUTH_ERROR(40101, ERROR, NOT_AUTH_DESCRIPTION),
    // 5. 系统内部错误
    SYSTEM_INNER_ERROR(50000, ERROR, SYSTEM_INNER_DESCRIPTION);

    // fields
    private final Integer code;
    private final String msg;
    private final String description;
    // 构造函数
    ErrorCode(Integer code, String msg, String description){
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public String getDescription(){
        return description;
    }


}
