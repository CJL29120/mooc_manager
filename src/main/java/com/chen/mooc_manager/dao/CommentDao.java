package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Comment;
import com.chen.mooc_manager.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {

    @Select("select id,content,user_id,avatar_url,create_time " +
            " from comment t " +
            " where t.section_id = #{sectionId}" +
            " order by t.create_time DESC")
    List<Comment> getBySectionId(Integer sectionId);

    @Select("select id,content,user_id,avatar_url,create_time " +
            " from comment t " +
            " where t.reply_id = #{id}" +
            " order by t.create_time")
    List<Comment> getByReply(Integer id);

    @Select("select id,content,user_id,avatar_url,create_time " +
            " from comment t " +
            " where t.course_id = #{courseId}" +
            " order by t.create_time DESC")
    List<Comment> getByCourseId(Integer courseId);
}
