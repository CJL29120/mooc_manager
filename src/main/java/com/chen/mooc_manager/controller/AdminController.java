package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.cache.InMemoryCacheStore;
import com.chen.mooc_manager.model.Callback;
import com.chen.mooc_manager.model.param.LoginParam;
import com.chen.mooc_manager.model.param.SignupParam;
import com.chen.mooc_manager.service.AdminService;
import com.chen.mooc_manager.service.MailService;
import com.chen.mooc_manager.util.OtherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
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
    public Results sendCode(@RequestParam("emailAddr")String emailAddr) {
        String code = OtherUtils.getRandomCode(4);
        cacheStore.put("code-"+emailAddr,code,2, TimeUnit.MINUTES);
        mailService.sendTextMail(emailAddr,"上课吧验证码","code:"+code);
        return Results.success();
    }

    @PostMapping("/signup")
    @ResponseBody
    public Results signup(SignupParam signupParam){
        return adminService.signup(signupParam)?Results.success():Results.failure();
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/admin/getCallback")
    @ResponseBody
    public Results<Callback> getCallback(PageTableRequest request){
        List<Callback> callbacks = adminService.getCallback(request.getOffset(),request.getLimit());
        return Results.success(adminService.getCallbackCount(),callbacks);
    }

    @GetMapping("/admin/getCallbackCount")
    @ResponseBody
    public Results getCallbackCount(){
        return Results.success(adminService.getCallbackCount());
    }

    @PostMapping("/admin/solveCallback")
    @ResponseBody
    public Results solveCallback(Integer id){
        return Results.success(adminService.changeCallbackStatus(id));
    }

    @PostMapping("/admin/addNotice")
    @ResponseBody
    public Results addNotice(@RequestParam("message")String message){
        return adminService.saveNotice(message) ? Results.success():Results.failure();
    }

    @GetMapping("/admin/getNotice")
    @ResponseBody
    public Results<Callback> getNotice(){
        List<Callback> callbacks = adminService.getNotice();
        return Results.success(callbacks);
    }
}
