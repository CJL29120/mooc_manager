package com.chen.mooc_manager.controller;


import com.alibaba.fastjson.JSONObject;
import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.dto.CourseAddDTO;
import com.chen.mooc_manager.model.*;
import com.chen.mooc_manager.model.dto.CourseShowDTO;
import com.chen.mooc_manager.model.param.CourseConditionParam;
import com.chen.mooc_manager.service.*;
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
import javax.websocket.server.PathParam;
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
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Autowired
    VerifyService verifyService;

    @Autowired
    CourseService courseService;

    @Autowired
    SectionService sectionService;

    @Autowired
    CommentService commentService;


    @GetMapping({"/", "/index", "/index.html"})
    public String index() {
        return "courses/index";
    }


    @GetMapping({"/addPage"})
    public String addPage(Model model, String id) {
        Assert.notNull(id,"创建课程的审核ID不能为空");
        model.addAttribute("verify", verifyService.getById(id));
        return "admin/course/course-add";
    }

    String pattern = "yyyy-MM-dd";

    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));// CustomDateEditor为自定义日期编辑器
    }

    @PostMapping({"/add"})
    @ResponseBody
    public Results<Course> add(CourseAddDTO course) {
        Assert.isTrue(verifyService.getById(course.getVerifyId()).getStatus() == 1, "此申请ID审核未通过，禁止创建课程");
        if(courseService.add(course) && verifyService.removeById(course.getVerifyId())){
            return Results.success();
        }
        return Results.failure(course);
    }

    @PostMapping("/list")
    @ResponseBody
    public Results<Course> list(PageTableRequest request) {
        Assert.notNull(request, "请求显示的页码参数不能为空");
        return courseService.getAllCoursesByPage(request.getOffset(),request.getLimit());
    }

    @PostMapping("deleteBatch")
    @ResponseBody
    public Results<Course> deleteBatch(@RequestParam("ids[]") List<String> ids) {
        Assert.notNull(ids, "删除的课程ID不能为空！");
        return courseService.removeByIds(ids) ? Results.success() : Results.failure();
    }

    @GetMapping("/editPage")
    public String editPage(Model model, String id) {
        Assert.notNull(id,"编辑课程的课程ID不能为空");
        model.addAttribute("course", courseService.getById(id));
        return "admin/course/course-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public Results<Course> edit(Course course) {
        Assert.notNull(course,"编辑课程的课程信息不能为空");
        return courseService.edit(course) ? Results.success():Results.failure(course);
    }


    @PostMapping("/listBy")
    @ResponseBody
    public Results listBy(PageTableRequest request, @RequestParam(value = "condition") String con) {
        Assert.notNull(request, "请求的页码参数不能为空");
        Assert.notNull(con, "请求条件参数不能为空");
        CourseConditionParam condition = JSONObject.parseObject(con, CourseConditionParam.class);
        return Results.success(courseService.countWithCondition(condition),courseService.getWithCondition(condition,request));
    }


    @GetMapping("/show")
    public String show(Model model,@RequestParam("id")Integer id) throws RuntimeException {
        CourseShowDTO dto = courseService.getShowDetail(id);
        log.info(dto.toString());
        model.addAttribute("course",dto);
        return "courses/show";
    }

    @GetMapping("/study")
    public String study(Model model, @RequestParam("courseId") Integer courseId, @RequestParam("sectionId")Integer sectionId, @RequestParam("userId")Integer userId){
        courseService.recordStudy(courseId, sectionId,userId);
        model.addAttribute("section",sectionService.getById(sectionId));
        model.addAttribute("commentDTOs",commentService.getCommentDto(sectionId));
        return "courses/study";
    }

}