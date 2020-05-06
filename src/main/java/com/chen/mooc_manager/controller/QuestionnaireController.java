package com.chen.mooc_manager.controller;

import com.alibaba.fastjson.JSON;
import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Questionnaire;
import com.chen.mooc_manager.model.Section;
import com.chen.mooc_manager.model.dto.QuestionnaireDTO;
import com.chen.mooc_manager.service.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("getById")
    @ResponseBody
    public Results<Questionnaire> getById(@RequestParam("qId")Integer qId){
        return Results.success(questionnaireService.getById(qId));
    }

    @GetMapping("getQuestionnairePageById")
    @ResponseBody
    public Results<QuestionnaireDTO> getQuestionnairePageById(PageTableRequest request, @RequestParam("teacherId")Integer teacherId){
        Assert.notNull(request, "请求显示的页码参数为空");
        List<QuestionnaireDTO> questionnaires =  questionnaireService.getQuestionnairePageById(request.getOffset(),request.getLimit(),teacherId);
        return Results.success(questionnaireService.getCountById(teacherId),questionnaires);
    }

    @GetMapping("question/list")
    @ResponseBody
    public Results<Questionnaire> getQuestion(@RequestParam("courseId") Integer courseId){
        return Results.success(questionnaireService.getQuestion(courseId));
    }


    @GetMapping("answer/list")
    @ResponseBody
    public Results<Questionnaire> getAnswer(@RequestParam("courseId") Integer courseId){
        return Results.success(questionnaireService.getAnswer(courseId));
    }

    @GetMapping("/editPage")
    public String editPage(Model model, String id) {
        model.addAttribute("qId", id);
        return "admin/questionnaire/questionnaire-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public Results saveAnswer(@RequestParam("qId")Integer qId,@RequestParam("ahtml")String ahtml){
        return questionnaireService.saveAnswer(qId,ahtml)?Results.success():Results.failure();
    }
}
