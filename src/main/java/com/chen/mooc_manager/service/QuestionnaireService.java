package com.chen.mooc_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mooc_manager.model.Questionnaire;
import com.chen.mooc_manager.model.dto.QuestionnaireDTO;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
public interface QuestionnaireService extends IService<Questionnaire> {
    boolean save(Questionnaire questionnaire);

    List<Questionnaire> getQuestion(Integer courseId);

    List<Questionnaire> getAnswer(Integer courseId);

    List<QuestionnaireDTO> getQuestionnairePageById(Integer offset, Integer limit, Integer teacherId);

    Integer getCountById(Integer teacherId);

    Boolean saveAnswer(Integer id,String ahtml);
}
