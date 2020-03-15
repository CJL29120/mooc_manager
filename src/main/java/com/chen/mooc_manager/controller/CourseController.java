package com.chen.mooc_manager.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.dto.CourseAddDTO;
import com.chen.mooc_manager.model.*;
import com.chen.mooc_manager.service.*;
import com.chen.mooc_manager.vo.CourseCommentVO;
import com.chen.mooc_manager.vo.CourseSectionVO;
import com.chen.mooc_manager.vo.CourseVO;
import com.chen.mooc_manager.model.dto.CourseSearchDTO;
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
import java.util.Date;
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
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Autowired
    VerifyService verifyService;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseSectionService sectionService;

    @Autowired
    CourseCommentService commentService;

    @Autowired
    CTCService ctcService;

    @Autowired
    TeacherService  teacherService;

    @Resource
    ModelMapper modelMapper;

    @GetMapping({"/","/index","/index.html"})
    private String index(){
        return "courses/index";
    }

    @GetMapping({"/add"})
    private String add(Model model,Verify verify){
        model.addAttribute("verify",verifyService.getById(verify.getId()));
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
    private Results<Course> add(CourseAddDTO course){
        Assert.isTrue(verifyService.getById(course.getVerifyId()).getStatus()==1,"此申请ID审核未通过，禁止创建课程");
        log.info(course.toString());
        course.setCreateTime(new Date());
        courseService.save(course);
        verifyService.removeById(course.getVerifyId());
        return Results.success();
    }


    @GetMapping("/list")
    @ResponseBody
    private Results<Course> list(PageTableRequest request){
        Assert.notNull(request,"请求显示的页码信息为空");
        request.countOffset();
        List<Course> courses = courseService.getAllCoursesByPage(request.getOffset(),request.getLimit());
        return Results.success(courseService.count(),courses);
    }

    @PostMapping("deleteBatch")
    @ResponseBody
    private Results<Course> deleteBatch(@RequestParam("ids") List<String> ids){
        Assert.notNull(ids,"请选择删除的课程！");
        if(courseService.removeByIds(ids)){
            return Results.success();
        }
        return Results.failure();
    }

    @GetMapping("/edit")
    private String edit(Model model,String id){
        model.addAttribute("course",courseService.getById(id));
        return "admin/course/course-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    private Results<Course> edit(Course course){
        log.info(course.toString());
        boolean res = courseService.update(course, new UpdateWrapper<Course>().lambda().set(Course::getName,course.getName())
                        .set(Course::getType,course.getType())
                        .set(Course::getPrice,course.getPrice())
                        .set(Course::getClassifyId,course.getClassifyId())
                        .set(Course::getClassifyName,course.getClassifyName())
                        .set(Course::getDirection,course.getDirection())
                        .set(Course::getShortIntro,course.getShortIntro())
                        .set(Course::getCoverUrl,course.getCoverUrl())
                        .eq(Course::getId,course.getId())
        );
        return res == true ?  Results.success():Results.failure();
    }

    //"/courses?type=1&classifyId=4&order=4&page=1"
    @GetMapping("")
    private String indexByOrder(@ModelAttribute CourseSearchDTO courseSearchDTO, Model model){
        log.info("type:"+ courseSearchDTO.toString());

        int type = courseSearchDTO.getType(),
                classifyId = Integer.parseInt(courseSearchDTO.getClassifyId()),
                order = courseSearchDTO.getOrder(),
                current = courseSearchDTO.getCurrent();

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

    @RequestMapping("/show/{id}")
    private String show(@PathVariable("id") String id,Model model){
        Course course = courseService.getById(id);
        CourseVO courseVO =modelMapper.map(course, CourseVO.class);

        Teacher creator = teacherService.getById(course.getCreatorId());
        courseVO.setCreator(creator);

        QueryWrapper<CourseSection> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CourseSection::getCourseId,id);
        List<Object> sections = sectionService.listObjs(wrapper);
        List<CourseSection> sections1  =sections.stream().map(item -> (CourseSection)item).collect(Collectors.toList());
        courseVO.setSections(sections1);



        System.out.println(courseVO.toString());


        model.addAttribute("course", courseVO);
        return "courses/show";
    }

    @RequestMapping("/study")
    private String study(@RequestParam int courseId,@RequestParam int sectionId,Model model){

        QueryWrapper<CourseSection> wrapper = new QueryWrapper();
        wrapper.lambda().eq(CourseSection::getCourseId,courseId).eq(CourseSection::getId,sectionId);
        CourseSection section = sectionService.getOne(wrapper);
        CourseSectionVO sectionVO = modelMapper.map(section, CourseSectionVO.class);


        QueryWrapper<CourseComment> wrapper1 = new QueryWrapper<>();
        wrapper1.lambda().eq(CourseComment::getSectionId,sectionId);
        List<CourseComment> list0 = commentService.list(wrapper1);
        List<CourseCommentVO> list = list0.stream().map(item -> modelMapper.map(item, CourseCommentVO.class)).collect(Collectors.toList());



        /*List<List<CourseComment>> list1 = list.stream().map(i ->{
            *//*commentService.getById(ctcService.getById(i.getCourseId()).getToCId())*//*
            QueryWrapper<CTC> wrapper2 = new QueryWrapper<>();
            wrapper2.lambda().eq(CTC::getCId,i.getCourseId());
            List<CourseComment> comments = ctcService.list(wrapper2).stream().map(ctc -> ctc.getToCId()).map(cid -> commentService.getById(cid)).collect(Collectors.toList());
            return comments;
        }).collect(Collectors.toMap(CourseComment::getCtcId,));

        list1.stream().map(i -> list.stream().map(vo -> {
            if(vo.getCourseId() == i.get(0).getCourseId())
            }
        }));*/

        model.addAttribute("section",sectionVO);
        return "courses/study";
    }
}

