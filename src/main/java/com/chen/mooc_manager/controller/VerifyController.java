package com.chen.mooc_manager.controller;


import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Verify;
import com.chen.mooc_manager.service.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Controller
@RequestMapping("/admin/verify")
@Slf4j
public class VerifyController {

    @Autowired
    VerifyService verifyService;

    @Resource
    ModelMapper modelMapper;

    @GetMapping("/apply")
    private String verify(Model model){
        model.addAttribute("verify",new Verify());
        return "admin/course/verify-apply";
    }

    String pattern = "yyyy-MM-dd";

    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));// CustomDateEditor为自定义日期编辑器
    }

    @PostMapping("/apply")
    @ResponseBody
    private Results<Verify> verify(Verify verify){
        Assert.notNull(verify,"申请信息为空");
        if(verifyService.save(verify)){
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @GetMapping("/list")
    @ResponseBody
    private Results<Verify> list(PageTableRequest request){
        Assert.notNull(request,"请求显示的页码信息为空");
        request.countOffset();
        List<Verify> verifies = verifyService.getAllChecksByPage(request.getOffset(),request.getLimit());
        return Results.success(verifyService.count(), verifies);
    }

    @PostMapping("/delete")
    @ResponseBody
    private Results<Verify> delete(String id){
        Assert.notNull(id,"请选择删除的申请！");
        if (verifyService.removeById(id)){
            return Results.success();
        }
        return Results.failure(500,"申请删除失败");
    }


    @PostMapping("/deleteBatch")
    @ResponseBody
    private Results<Verify> deleteBatch(@RequestParam(value="ids[]")List<String> ids){
        Assert.notNull(ids,"请选择删除的申请！");
        if (verifyService.removeByIds(ids)){
            return Results.success();
        }
        return Results.failure(500,"申请批量删除失败");
    }



}

