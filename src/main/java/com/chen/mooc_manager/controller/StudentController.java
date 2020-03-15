package com.chen.mooc_manager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.model.param.StudentParam;
import com.chen.mooc_manager.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Controller
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    StudentService studentService;

    @Resource
    ModelMapper modelMapper;

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("student",new StudentParam());
        return "admin/student/student-add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Results<Student> save(StudentParam param){
        log.info(param.toString());
        Student student = modelMapper.map(param, Student.class);
        log.info(student.toString());
        if(studentService.save(student)){
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Results<Student> delete(Integer id){
        Assert.notNull(id,"请选择删除的用户！");
        if(studentService.removeById(id)){
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Results<Student> deleteBatch(@RequestParam(value="ids[]") List<String> ids){
        Assert.notNull(ids,"请选择删除的用户！");
        if(studentService.removeByIds(ids)){
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @GetMapping(value = "/edit")
    public String editUser(Model model, Student student) {
        model.addAttribute("student",studentService.getById(student.getId()));
        return "admin/student/student-edit";
    }

    String pattern = "yyyy-MM-dd";

    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));// CustomDateEditor为自定义日期编辑器
    }

    @PostMapping("/edit")
    @ResponseBody
    public Results<Student> update(StudentParam param){
        log.info(param.toString());
        Student student = modelMapper.map(param, Student.class);
        if(studentService.updateById(student)){
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @GetMapping("/list")
    @ResponseBody
    public Results<Student> list(PageTableRequest request){
        request.countOffset();
        List<Student> students = studentService.getAllUsersByPage(request.getOffset(),request.getLimit());
        return Results.success(studentService.count(),students);
    }
}

