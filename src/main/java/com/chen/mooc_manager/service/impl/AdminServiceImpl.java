package com.chen.mooc_manager.service.impl;

import com.chen.mooc_manager.cache.InMemoryCacheStore;
import com.chen.mooc_manager.exception.ParamWrongException;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.model.param.LoginParam;
import com.chen.mooc_manager.model.param.SignupParam;
import com.chen.mooc_manager.service.AdminService;
import com.chen.mooc_manager.service.StudentService;
import com.chen.mooc_manager.util.ParamUtil;
import com.chen.mooc_manager.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    ParamUtil paramUtil;

    @Autowired
    StudentService studentService;

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
            Student student = new Student();
            student.setEmail(signupParam.getEmail());
            student.setPassword(new BCryptPasswordEncoder().encode(signupParam.getPassword()));
            return studentService.save(student);
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
}
