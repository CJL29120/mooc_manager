package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.QuestionnaireDao;
import com.chen.mooc_manager.model.Questionnaire;
import com.chen.mooc_manager.model.Verify;
import com.chen.mooc_manager.service.QuestionnaireService;
import com.chen.mooc_manager.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireDao, Questionnaire> implements QuestionnaireService {

    @Resource
    QuestionnaireDao questionnaireDao;

    @Autowired
    ParamUtil<Questionnaire> paramUtil;

    @Override
    public boolean save(Questionnaire questionnaire){
        questionnaire = paramUtil.afterTrim(questionnaire);
        questionnaire.setCreateTime(new Date());
        return super.save(questionnaire);
    }

    @Override
    public List<Questionnaire> getQuestion(Integer courseId) {
        return questionnaireDao.getQuestion(courseId);
    }

    @Override
    public List<Questionnaire> getAnswer(Integer courseId) {
        return questionnaireDao.getAnswer(courseId);
    }

}
