package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.mooc_manager.cache.InMemoryCacheStore;
import com.chen.mooc_manager.dao.CallbackDao;
import com.chen.mooc_manager.exception.ParamWrongException;
import com.chen.mooc_manager.model.Callback;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.model.User;
import com.chen.mooc_manager.model.param.LoginParam;
import com.chen.mooc_manager.model.param.SignupParam;
import com.chen.mooc_manager.service.AdminService;
import com.chen.mooc_manager.service.StudentService;
import com.chen.mooc_manager.service.UserService;
import com.chen.mooc_manager.util.ParamUtil;
import com.chen.mooc_manager.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    ParamUtil paramUtil;

    @Autowired
    UserService userService;

    @Resource
    CallbackDao callbackDao;

    @Resource
    InMemoryCacheStore cacheStore;

    public boolean signup(SignupParam signupParam){
        Assert.notNull(signupParam,"注册参数不能为空！");
        Assert.hasText(signupParam.getCode(),"验证码不能为空");
        Assert.hasText(signupParam.getEmail(),"邮箱不能为空");
        signupParam = (SignupParam) paramUtil.afterTrim(signupParam);

        if(!ValidateUtils.isEmail(signupParam.getEmail())){
            throw new ParamWrongException("邮箱格式不正确！");
        }
        if(!signupParam.getPassword().equals(signupParam.getRepassword())){
            throw new ParamWrongException("输入密码不一致");
        }
        Optional<String> optionalCredential = cacheStore.get("code-" + signupParam.getEmail());
        optionalCredential.orElseThrow(()-> new ParamWrongException("验证码已过期,请重新获取验证码!"));


        if (signupParam.getCode().equals(optionalCredential.get())) {
            User user = new User();
            user.setEmail(signupParam.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(signupParam.getPassword()));
            return userService.saveAsStudent(user);
        }
        throw new ParamWrongException("输入的验证码不正确");
    }

    public boolean authenticate(LoginParam loginParam){
        return false;
    }

    public void modifyPersonalInfo(){

    }

    public void inform(){

    }

    @Override
    public boolean saveCallback(Integer userId,String message) {
        Callback callback = new Callback();
        callback.setUserId(userId);
        callback.setMessage(message);
        callback.setCreateTime(new Date());
        return callbackDao.insert(callback) == 1;
    }

    @Override
    public List<Callback> getCallback(Integer startPosition,Integer limit) {
        return callbackDao.getCallback(startPosition,limit);
    }

    @Override
    public Integer getCallbackCount() {
        return callbackDao.getCallbackCount();
    }

    @Override
    public Boolean changeCallbackStatus(Integer id) {
        return callbackDao.updateCallbackStatus(id);
    }

    @Override
    public Boolean saveNotice(String message) {
        Callback notice = new Callback();
        notice.setUserId(1);
        notice.setType(3);
        notice.setMessage(message);
        notice.setCreateTime(new Date());
        return callbackDao.insert(notice) == 1;
    }

    @Override
    public List<Callback> getNotice() {
        return callbackDao.getNotice();
    }
}
