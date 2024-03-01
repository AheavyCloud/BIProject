package com.zjh.backend.service.impl;

import com.zjh.backend.mapper.UserMapper;
import com.zjh.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

}
