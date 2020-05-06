package com.chen.mooc_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.model.dto.CourseShowDTO;
import com.chen.mooc_manager.model.param.CourseConditionParam;

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

    List<Course> getWithCondition(CourseConditionParam condition, PageTableRequest request);

    Integer countWithCondition(CourseConditionParam condition);

    CourseShowDTO getShowDetail(Integer courseId) throws RuntimeException;

    boolean recordStudy(Integer courseId,Integer sectionId,Integer userId);

    Integer getCountById(Integer creatorId);

    List<Course> getCoursePageById(Integer offset, Integer limit, Integer creatorId);
}
