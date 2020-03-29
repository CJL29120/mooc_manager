package com.chen.mooc_manager.controller;

import com.alibaba.fastjson.JSON;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Questionnaire;
import com.chen.mooc_manager.service.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("questionnaire")
@Slf4j
public class QuestionnaireController{

    @Autowired
    QuestionnaireService questionnaireService;

    @PostMapping("/add")
    @ResponseBody
    public Results addQuestionnaire(@RequestParam("questionnaire") String questionnaireStr){
        Questionnaire questionnaire = JSON.parseObject(questionnaireStr,Questionnaire.class);
        return questionnaireService.save(questionnaire)?Results.success():Results.failure();
    }

    @GetMapping("question/list")
    @ResponseBody
    public Results<Questionnaire> getQuestion(@RequestParam("courseId") Integer courseId){
        log.info(questionnaireService.getQuestion(courseId).toString());
        return Results.success(questionnaireService.getQuestion(courseId));
    }

    @GetMapping("answer/list")
    @ResponseBody
    public Results<Questionnaire> getAnswer(@RequestParam("courseId") Integer courseId){
        log.info(questionnaireService.getAnswer(courseId).toString());
        return Results.success(questionnaireService.getAnswer(courseId));
    }
}
