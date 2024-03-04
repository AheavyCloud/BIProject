package com.zjh.backend.model.dto.requestbody_.userequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
