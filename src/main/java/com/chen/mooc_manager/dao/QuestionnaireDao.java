package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Questionnaire;
import com.chen.mooc_manager.model.Verify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Mapper
public interface QuestionnaireDao extends BaseMapper<Questionnaire> {

    @Select("select id,title,q_html,a_html,create_time,follow_count " +
            " from questionnaire t " +
            " where t.course_id = #{courseId} and t.solved = 0 " +
            " order by t.create_time desc ")
    List<Questionnaire> getQuestion(Integer courseId);

    @Select("select id,title,q_html,a_html,create_time,follow_count " +
            " from questionnaire t " +
            " where t.course_id = #{courseId} and t.solved != 0 " +
            " order by t.create_time desc ")
    List<Questionnaire> getAnswer(Integer courseId);
}
