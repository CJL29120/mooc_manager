package com.chen.mooc_manager.service;

import com.chen.mooc_manager.model.Callback;
import com.chen.mooc_manager.model.param.LoginParam;
import com.chen.mooc_manager.model.param.SignupParam;

import java.util.List;

public interface AdminService {

    boolean signup(SignupParam signupParam);

    boolean authenticate(LoginParam loginParam);

    void modifyPersonalInfo();

    void inform();

    boolean saveCallback(Integer userId,String message);

    List<Callback> getCallback(Integer startPosition,Integer limit);

    Integer getCallbackCount();

    Boolean changeCallbackStatus(Integer id);

    Boolean saveNotice(String message);

    List<Callback> getNotice();
}
