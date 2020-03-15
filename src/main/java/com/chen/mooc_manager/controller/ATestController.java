package com.chen.mooc_manager.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Controller
public class ATestController {

    @Autowired
    CourseService service;

    @RequestMapping("/a")
    @ResponseBody
    String test(){
        QueryWrapper<Course> wrapper = new QueryWrapper();
        wrapper.lambda().eq(Course::getDeleted,0);
        JSONArray array =new JSONArray();

        return JSON.toJSONString(service.list(wrapper));
    }

    @RequestMapping("/admin")
    String admin(){
        return "admin/index";
    }

    @RequestMapping("/list")
    String list(){
        return "admin/student/student-list";
    }

}

