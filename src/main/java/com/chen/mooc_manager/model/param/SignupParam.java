package com.chen.mooc_manager.model.param;

import lombok.Data;

@Data
public class SignupParam {

    String email;
    String password;
    String repassword;
    String code;
}
