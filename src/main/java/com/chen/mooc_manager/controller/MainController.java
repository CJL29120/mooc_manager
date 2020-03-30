package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.cache.InMemoryCacheStore;
import com.chen.mooc_manager.service.CourseService;
import com.chen.mooc_manager.service.MailService;
import com.chen.mooc_manager.util.OtherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    CourseService courseService;


    @RequestMapping({"/","/index","/index.html"})
    public String index(Model model){
        model.addAttribute("courses",courseService.getRecommends(6));
        model.addAttribute("vipCourses",courseService.getVipCourses(4));
        return "index";
    }

}
