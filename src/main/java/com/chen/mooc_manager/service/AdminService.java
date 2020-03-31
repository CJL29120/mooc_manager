package com.chen.mooc_manager.service;

import com.chen.mooc_manager.model.param.LoginParam;
import com.chen.mooc_manager.model.param.SignupParam;

public interface AdminService {

    boolean signup(SignupParam signupParam);

    boolean authenticate(LoginParam loginParam);

    void modifyPersonalInfo();

    void inform();

}
