package com.zjh.backend.service;
import com.zjh.backend.pojo.UserLoginRequest;
import com.zjh.backend.pojo.UserRegisterRequest;

import java.util.List;

public interface UserService {

    public Long doRegister(UserRegisterRequest registerRequest);
    public String doLogin(UserLoginRequest loginRequest);

}
