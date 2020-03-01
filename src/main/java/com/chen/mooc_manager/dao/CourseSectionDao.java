package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.CourseSection;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程评论&答疑 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Mapper
public interface CourseSectionDao extends BaseMapper<CourseSection> {

}
