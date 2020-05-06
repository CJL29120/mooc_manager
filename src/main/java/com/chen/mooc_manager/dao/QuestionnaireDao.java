package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Questionnaire;
import com.chen.mooc_manager.model.Verify;
import com.chen.mooc_manager.model.dto.QuestionnaireDTO;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.*;

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

    @Select(" select q.id,c.name as courseName,q.title,q.solved,q.create_time " +
            " from questionnaire q left join course c on q.course_id = c.id " +
            " where c.creator_id = #{teacherId} " +
            " order by q.solved " +
            " limit #{offset},#{limit}")
    List<QuestionnaireDTO> getQuestionnairePageById(Integer offset, Integer limit, Integer teacherId);

    @Select(" select count(q.id) " +
            " from questionnaire q right join course c on q.course_id = c.id " +
            " where c.creator_id = #{teacherId} ")
    Integer getCountById(Integer teacherId);

    @Update(" update questionnaire t set t.a_html = #{ahtml},t.solved = 1 where t.id = #{id}")
    Boolean saveAnswer(Integer id,@Param("ahtml") String ahtml);
}
