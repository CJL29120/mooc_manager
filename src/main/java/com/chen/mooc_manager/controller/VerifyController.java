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
@RequestMapping("/verify")
@Slf4j
public class VerifyController {

    @Autowired
    VerifyService verifyService;

    String pattern = "yyyy-MM-dd";

    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));// CustomDateEditor为自定义日期编辑器
    }

    @PostMapping("/add")
    @ResponseBody
    public Results<Verify> add(Verify verify){
        Assert.notNull(verify,"申请课程的课程信息不能为空");
        return verifyService.save(verify) ? Results.success() : Results.failure(500,"申请添加失败");
    }

    @GetMapping("/listByCreator")
    @ResponseBody
    public Results<Verify> getByCreator(PageTableRequest request,@RequestParam("creatorId") Integer creatorId){
        Assert.notNull(request,"请求显示的页码参数不能为空");
        List<Verify> verifies = verifyService.getPageByCreator(request.getOffset(),request.getLimit(),creatorId);
        return Results.success(verifyService.getCountByCreator(creatorId), verifies);
    }

    @GetMapping("/unhandled")
    @ResponseBody
    public Results<Verify> getUnhandled(PageTableRequest request){
        Assert.notNull(request,"请求显示的页码参数不能为空");
        List<Verify> unhandled = verifyService.getUnhandledByPage(request.getOffset(),request.getLimit());
        return Results.success(verifyService.getUnhandledCount(), unhandled);
    }

    @PostMapping("/enable")
    @ResponseBody
    public Results enable(@RequestParam("id")Integer id){
        Assert.notNull(id,"审核的申请ID不能为空");
        return verifyService.enableById(id) ? Results.success() : Results.failure(500,"审核通过失败");
    }

    @PostMapping("/disable")
    @ResponseBody
    public Results disable(@RequestParam("id")Integer id){
        Assert.notNull(id,"审核的申请ID不能为空");
        return verifyService.disableById(id) ? Results.success() : Results.failure(500,"审核不通过失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Results<Verify> delete(String id){
        Assert.notNull(id,"删除的申请ID不能为空");
        return verifyService.removeById(id) ? Results.success() : Results.failure(500,"申请删除失败");
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Results<Verify> deleteBatch(@RequestParam(value="ids[]")List<String> ids){
        Assert.notNull(ids,"删除的申请ID不能为空");
        return verifyService.removeByIds(ids) ? Results.success() : Results.failure(500,"申请批量删除失败");
    }
}

