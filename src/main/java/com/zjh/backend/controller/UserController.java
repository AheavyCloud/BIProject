package com.zjh.backend.controller;

import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import com.zjh.backend.pojo.Result;
import com.zjh.backend.model.dto.requestbody_.userequest.UserLoginRequest;
import com.zjh.backend.model.dto.requestbody_.userequest.UserRegisterRequest;
import com.zjh.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user") // 提取统一的前置路径
public class UserController {
    @Autowired
    private UserService userService;

    // 注册 register -- post
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterRequest registerRequest){

        if(registerRequest == null) {
            log.info("注册失败，信息为空···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }
        // 执行service层程序
        Long id = userService.doRegister(registerRequest);
        if(id == null) {
            throw new BIException(ErrorCode.PARAM_ERROR);
        }
        return Result.success(id.toString());
    }

    // 登录 login -- post
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginRequest loginRequest){

        if(loginRequest == null)
            throw new BIException(ErrorCode.PARAM_ERROR);

        // 执行service层程序
        String jwt = userService.doLogin(loginRequest);
        if(jwt == null)
            throw new BIException(ErrorCode.PARAM_ERROR);
        log.info("登录成功···jwt" + jwt);
        return Result.success(jwt);

//        return null;
    }

    // 查询 get -- get

    // 删除 remove -- delete

}
