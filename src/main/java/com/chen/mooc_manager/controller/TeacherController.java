package com.chen.mooc_manager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Teacher;
import com.chen.mooc_manager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 教师表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService service;

    @RequestMapping("list")
    @ResponseBody
    public Results<Teacher> list(){
        return Results.success(4,service.list(new QueryWrapper<Teacher>().lambda().eq(Teacher::getStatus,2)));
    }
}

