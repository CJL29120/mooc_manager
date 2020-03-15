package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.CourseDao;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Course> getAllCoursesByPage(Integer startPosition,Integer limit) {
        return courseDao.getAllCoursesByPage(startPosition, limit);
    }
}
