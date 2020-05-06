package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.dao.*;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.Section;
import com.chen.mooc_manager.model.Study;
import com.chen.mooc_manager.model.dto.CourseShowDTO;
import com.chen.mooc_manager.model.param.CourseConditionParam;
import com.chen.mooc_manager.service.CourseService;
import com.chen.mooc_manager.util.ParamUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
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

    @Resource
    SectionDao sectionDao;


    @Resource
    StudyDao studyDao;

    @Resource
    UserDao userDao;

    @Autowired
    ParamUtil<Course> paramUtil;

    @Resource
    ModelMapper modelMapper;

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
    public List<Course> getWithCondition(CourseConditionParam condition, PageTableRequest request) {
        List<List> list = condition.getMap();
        Map hashMap = new HashMap();
        list.forEach(l -> hashMap.put(l.get(0),l.get(1)));
//        log.info(hashMap.toString());
        String orderBy = (String)hashMap.remove("orderBy");
        String orderDirection = (String) hashMap.remove("orderDirection");

        return courseDao.selectWithCondition(hashMap, orderBy,orderDirection,request.getOffset(),request.getLimit());
    }

    @Override
    public  Integer countWithCondition(CourseConditionParam condition){
        List<List> list = condition.getMap();
        Map hashMap = new HashMap();
        list.forEach(l -> hashMap.put(l.get(0),l.get(1)));
        hashMap.remove("orderBy");
        hashMap.remove("orderDirection");
        return courseDao.countWithCondition(hashMap);
    }

    @Override
    public CourseShowDTO getShowDetail(Integer courseId) throws RuntimeException {
        CourseShowDTO dto = modelMapper.map(courseDao.selectById(courseId), CourseShowDTO.class);

        List<Section> sections = sectionDao.selectList(new QueryWrapper<Section>().lambda().eq(Section::getCourseId,courseId));
        dto.setSections(sections);
        dto.setCreator(Optional.ofNullable(userDao.getTeacherById(dto.getCreatorId())).orElseThrow(() -> new RuntimeException("返回课程创建者为空")));

        return dto;
    }

    @Override
    public boolean recordStudy(Integer courseId, Integer sectionId,Integer userId) {
        Study study = new Study();
        study.setCourseId(courseId);
        study.setSectionId(sectionId);
        study.setUserId(userId);
        study.setCreateTime(new Date());
        return studyDao.insert(study) == 1;
    }

    @Override
    public Integer getCountById(Integer creatorId) {
        return courseDao.getCountById(creatorId);
    }

    @Override
    public List<Course> getCoursePageById(Integer offset, Integer limit, Integer creatorId) {
        return courseDao.getCoursePageById(offset,limit,creatorId);
    }

}
