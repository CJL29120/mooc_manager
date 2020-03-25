package com.chen.mooc_manager.controller;


import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.dao.HtmlDao;
import com.chen.mooc_manager.model.Html;
import com.chen.mooc_manager.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Controller
@Slf4j
public class ATestController {

    @Autowired
    CourseService service;

    @Resource
    HtmlDao htmlDao;

    @RequestMapping("/admin")
    String admin(){
        return "admin/index";
    }

    @RequestMapping("/list")
    String list(){
        return "admin/student/student-list";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }


    @PostMapping("/test")
    @ResponseBody
    public Results<String> test_html(@RequestParam("htmlJson") String htmlJson){
        Html html = new Html();
        html.setHtmlString(htmlJson);
        log.info("执行test Post方法");
        htmlDao.insert(html);
        return Results.success();
    }

    @PostMapping("/testGet")
    @ResponseBody
    public Results<Html> get_test_html(@RequestParam("id") Integer id){
        return Results.success(htmlDao.selectById(id));
    }
}

