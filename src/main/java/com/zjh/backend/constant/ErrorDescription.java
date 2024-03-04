package com.zjh.backend.constant;

public interface ErrorDescription {
    String ERROR = "error";
    String PARAM_DESCRIPTION = "参数错误";
    String NULL_DESCRIPTION = "请求参数为空错误";
    String NOT_LOGIN_DESCRIPTION = "未登录错误";
    String NOT_AUTH_DESCRIPTION = "没有权限错误";
    String SYSTEM_INNER_DESCRIPTION = "系统内部错误";
    String ERROR_TOO_MANY_REQUEST = "请求次数太多";

}
