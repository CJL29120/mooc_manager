package com.chen.mooc_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {


    @RequestMapping({"/","/index","/index.html"})
    private String index(){

        return "index";
    }

}
