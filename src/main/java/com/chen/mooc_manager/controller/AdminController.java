package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.cache.InMemoryCacheStore;
import com.chen.mooc_manager.model.param.LoginParam;
import com.chen.mooc_manager.model.param.SignupParam;
import com.chen.mooc_manager.service.AdminService;
import com.chen.mooc_manager.service.MailService;
import com.chen.mooc_manager.util.OtherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Controller
public class AdminController {

    @Autowired
    MailService mailService;

    @Autowired
    AdminService adminService;

    @Resource
    InMemoryCacheStore cacheStore;



    @GetMapping("/mailCode")
    @ResponseBody
    public Results sendCode(@RequestParam("emailAddr")String emailAddr){
        String code = OtherUtils.getRandomCode(6);
        cacheStore.put("code-"+emailAddr,code,2, TimeUnit.MINUTES);
        mailService.sendTextMail(emailAddr,"上课吧验证码","code:"+code);
        return Results.success();
    }

    @PostMapping("/signup")
    @ResponseBody
    public Results signup(SignupParam signupParam) throws Exception {
        return adminService.signup(signupParam)?Results.success():Results.failure();
    }

    @PostMapping("/login")
    @ResponseBody
    public Results login(@RequestParam("loginParam") LoginParam loginParam) throws Exception {
        return Results.success();
    }

}
