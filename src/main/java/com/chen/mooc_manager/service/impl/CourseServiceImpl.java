package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.dao.CourseDao;
import com.chen.mooc_manager.dao.RecommendDao;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.service.CourseService;
import com.chen.mooc_manager.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    CourseDao courseDao;

    @Resource
    RecommendDao recommendDao;

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

    @Override
    public List<Course> getRecommends(Integer limit) {
        Assert.isTrue(limit>0,"获取推荐课程的数量不能小于0");
        List<Integer> courseIds = recommendDao.getRecommends(limit).stream().map(
                rcd -> rcd.getCourseId()).collect(Collectors.toList());
        return courseDao.selectBatchIds(courseIds);
    }

    @Override
    public List<Course> getVipCourses(Integer limit) {
        Assert.isTrue(limit>0,"获取积分课程的数量不能小于0");
        return courseDao.getVipCourses(limit);
    }

    @Override
    public List<Course> getWithCondition(Map map, String orderBy, String orderDirection,Integer offset,Integer limit) {
        return courseDao.selectWithCondition(map, orderBy,orderDirection,offset,limit);
    }

    @Override
    public  Integer countWithCondition(Map map){
        return courseDao.countWithCondition(map);
    }

}
