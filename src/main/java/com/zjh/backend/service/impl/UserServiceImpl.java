package com.zjh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import com.zjh.backend.mapper.UserMapper;
import com.zjh.backend.pojo.entity.User;
import com.zjh.backend.model.dto.requestbody_.userequest.UserLoginRequest;
import com.zjh.backend.model.dto.requestbody_.userequest.UserRegisterRequest;
import com.zjh.backend.security_.JwtProvider;
import com.zjh.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private static final String SALT = "zhangjiahao";
    private static final String USER_LOGIN_STATE = "userLoginState";
    @Autowired
    UserMapper userMapper;

    @Override
    public Long doRegister(UserRegisterRequest registerRequest) {
        // 参数校验
        // 空格校验
        log.info(registerRequest.toString());
        if(StringUtils.isAnyBlank(registerRequest.getUserAccount(),
                registerRequest.getUserPassword(),
                registerRequest.getCheckPassword())){
            log.info("注册失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }

        // 账号长度检测
        if(registerRequest.getUserAccount().length() < 4) {
            log.info("注册失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }

        // 密码和账号长度检测
        if(registerRequest.getUserPassword().length() < 8 ||
            registerRequest.getCheckPassword().length() < 8) {
            log.info("注册失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }

        // 校验码检测
        if(!registerRequest.getUserPassword().equals(
                registerRequest.getCheckPassword())) {
            log.info("注册失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }

        // 密码加密
        String encryPassword =  DigestUtils.md5DigestAsHex(
                (SALT + registerRequest.getUserPassword()).getBytes());

        // 插入数据
        User user = new User();
        user.setUserAccount(registerRequest.getUserAccount());
        // 判断数据是否重复
//        boolean result = this.save(user);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(user);
        // 数据重复
        if(userMapper.exists(userQueryWrapper)){
            log.info("注册失败···已包含该用户名");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }

        user.setUserPassword(encryPassword);
        int result = userMapper.insert(user);
        if(result <= 0) {
            log.info("注册失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }
        log.info("注册成功！ ID: " + user.getId().toString());
        return user.getId();

    }

    @Override
    public String doLogin(UserLoginRequest loginRequest) {

        // 空格校验
        if(StringUtils.isAnyBlank(loginRequest.getUserAccount(),
                loginRequest.getUserPassword())){
            log.info("登录失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }

        // 账号长度检测
        if(loginRequest.getUserAccount().length() < 4) {
            log.info("登录失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }

        // 密码和账号长度检测
        if(loginRequest.getUserPassword().length() < 8) {
            log.info("登录失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }

        // 通过获取mysql数据库信息
        User user = userMapper.selectByUserAccount(loginRequest.getUserAccount());
        if(user == null){
            log.info("登录失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }
        // 密码校验
        String encryCode =  DigestUtils.md5DigestAsHex(
                (SALT + loginRequest.getUserPassword()).getBytes());
        if(!user.getUserPassword().equals(encryCode)){
            log.info("密码错误，登录失败···");
            throw new BIException(ErrorCode.PARAM_ERROR);
        }
        // 登录成功利用spring security进行jwt生成令牌
        String jwt = JwtProvider.generateJWToken(user);

        return jwt;
    }
}
