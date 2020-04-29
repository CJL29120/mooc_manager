package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.base.result.WiselyMessage;
import com.chen.mooc_manager.base.result.WiselyResponse;
import com.chen.mooc_manager.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class WsController {

    @Autowired
    AdminService adminService;
    /**
     * 1.当浏览器向服务器发送请求时，通过@MessageMapping映射
     * 2.当服务器有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
     *
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping(value = "/callback")
    @SendTo(value = "/topic/callback")
    public Results saveCallback(WiselyMessage message){
        log.info(message.toString());
        return adminService.saveCallback(message.getUserId(),message.getMessage())?Results.success():Results.failure();
    }


    /*@MessageMapping(value = "/callback")
    @SendTo(value = "/topic/getResponse")
    public WiselyResponse say(WiselyMessage wiselyMessage){
        // 等待三秒才响应
//        Thread.sleep(3000);
        // 给全网响应广播内容
        wiselyResponse.setResponseMessage("广播：" + wiselyMessage.getMessage());
        log.info("广播内容: " + wiselyMessage.getMessage());
        return wiselyResponse;
    }*/
}
