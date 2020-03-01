package com.chen.mooc_manager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.mooc_manager.dto.CourseDTO;
import com.chen.mooc_manager.dto.CourseSearch;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.CourseSection;
import com.chen.mooc_manager.model.Teacher;
import com.chen.mooc_manager.service.CourseSectionService;
import com.chen.mooc_manager.service.CourseService;
import com.chen.mooc_manager.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Controller
@RequestMapping("/courses")
@Slf4j
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseSectionService sectionService;

    @Autowired
    TeacherService  teacherService;

    @Resource
    ModelMapper modelMapper;

    @RequestMapping({"/","/index","/index.html"})
    private String index(){
        return "courses/index";
    }

    @RequestMapping("/show/{id}")
    private String show(@PathVariable("id") String id,Model model){
        Course course = courseService.getById(id);
        CourseDTO courseDTO =modelMapper.map(course,CourseDTO.class);

        Teacher creator = teacherService.getById(course.getCreatorId());
        courseDTO.setCreator(creator);

        QueryWrapper<CourseSection> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CourseSection::getCourseId,id);
        List<Object> sections = sectionService.listObjs(wrapper);
        List<CourseSection> sections1  =sections.stream().map(item -> (CourseSection)item).collect(Collectors.toList());
        courseDTO.setSections(sections1);



        System.out.println(courseDTO.toString());


        model.addAttribute("course",courseDTO);
        return "courses/show";
    }

    //"/courses?type=1&classifyId=4&order=4&page=1"
    @RequestMapping("")
    private String indexByOrder(@ModelAttribute CourseSearch courseSearch, Model model){
        log.info("type:"+courseSearch.toString());

        int type = Integer.parseInt(courseSearch.getType()),
        classifyId = Integer.parseInt(courseSearch.getClassifyId()),
        order = courseSearch.getOrder(),
        current = courseSearch.getCurrent();

        IPage page = new Page(current,10);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        if (type >= 0){ wrapper.lambda().eq(Course::getType,type); }
        if (classifyId >= 0){ wrapper.lambda().eq(Course::getClassifyId,classifyId); }

        wrapper.lambda().orderByDesc((order==0)?Course::getCreateTime:Course::getWeight);
        List<Course> list = courseService.list(wrapper);

        Page<Course> coursePage = (Page<Course>) courseService.page(page,wrapper);
        log.info("total："+coursePage.getTotal());
        log.info("current："+coursePage.getCurrent());
        log.info("size："+coursePage.getSize());
        log.info("list："+coursePage.getRecords());

        list.forEach(System.out::println);
        model.addAttribute("courses",list);
        return "courses/index";
    }

}

