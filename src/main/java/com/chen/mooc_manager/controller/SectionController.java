package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Section;
import com.chen.mooc_manager.service.SectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

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
@RequestMapping("/section")
@Slf4j
public class SectionController {

    @Autowired
    SectionService sectionService;

    @GetMapping("/listPage")
    public String listPage(Model model,@RequestParam("courseId")Integer courseId){
        model.addAttribute("courseId",courseId);
        return "admin/section/section-list";
    }

    @GetMapping("/getSectionPageById")
    @ResponseBody
    public Results<Section> getSectionPageById(PageTableRequest request,@RequestParam("courseId")Integer courseId){
        Assert.notNull(request, "请求显示的页码参数为空");
        List<Section> sections =  sectionService.getSectionPageById(request.getOffset(),request.getLimit(),courseId);
        return Results.success(sectionService.getCountById(courseId),sections);
    }


    @GetMapping("/addPage")
    public String addPage(Model model,@RequestParam("courseId")Integer courseId){
        model.addAttribute("courseId",courseId);
        return "admin/section/section-add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Results<Section> add(Section section){
        Assert.notNull(section,"创建章节的章节信息不能为空");
        return sectionService.add(section) ? Results.success() : Results.failure(section);
    }

    @GetMapping("/editPage")
    public String editPage(Model model, String id){
        model.addAttribute("section",sectionService.getById(id));
        return "admin/section/section-edit";
    }

    String pattern = "yyyy-MM-dd";

    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));// CustomDateEditor为自定义日期编辑器
    }

    @PostMapping("/edit")
    @ResponseBody
    public Results<Section> edit(Section section){
        Assert.notNull(section,"编辑章节的章节信息不能为空");
        return sectionService.edit(section) ? Results.success() : Results.failure(section);
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Results<Section> deleteBatch(@RequestParam("ids[]") List<String> ids){
        return sectionService.removeByIds(ids) ? Results.success() : Results.failure();
    }

}

