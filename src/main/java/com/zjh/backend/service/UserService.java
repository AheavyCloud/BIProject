package com.zjh.backend.service;
import com.zjh.backend.pojo.requestbody_.userequest.UserLoginRequest;
import com.zjh.backend.pojo.requestbody_.userequest.UserRegisterRequest;

public interface UserService {

    public Long doRegister(UserRegisterRequest registerRequest);
    public String doLogin(UserLoginRequest loginRequest);

}
