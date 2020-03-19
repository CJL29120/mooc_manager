package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.dao.CourseDao;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.service.CourseService;
import com.chen.mooc_manager.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    ParamUtil<Course> paramUtil;

    @Override
    public boolean add(Course course) {
        course = paramUtil.afterTrim(course);
        course.setCreateTime(new Date());
        return super.save(course);
    }

    @Override
    public boolean edit(Course course) {
        course = paramUtil.afterTrim(course);
        course.setUpdateTime(new Date());
        return super.updateById(course);
    }

    @Override
    public Results<Course> getAllCoursesByPage(Integer startPosition, Integer limit) {
       List<Course> courses = courseDao.getAllCoursesByPage(startPosition, limit);
        return Results.success(super.count(),courses);
    }


}
