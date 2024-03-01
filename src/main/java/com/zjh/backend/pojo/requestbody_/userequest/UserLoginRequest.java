package com.zjh.backend.pojo.requestbody_.userequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    private String userAccount;
    private String userPassword;
}
