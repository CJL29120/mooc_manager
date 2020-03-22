package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
