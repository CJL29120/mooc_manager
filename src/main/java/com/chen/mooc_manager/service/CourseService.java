package com.chen.mooc_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.Student;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
public interface CourseService extends IService<Course> {

    boolean add(Course course);

    boolean edit(Course course);

    Results<Course> getAllCoursesByPage(Integer startPosition, Integer limit);

    List<Course> getRecommends(Integer limit);

    List<Course> getVipCourses(Integer limit);

    List<Course> getWithCondition(Map map,String orderBy,String orderDirection,Integer offset,Integer limit);

    Integer countWithCondition(Map map);
}
