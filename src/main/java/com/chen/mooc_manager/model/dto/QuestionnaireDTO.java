package com.chen.mooc_manager.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class QuestionnaireDTO{

    private Integer id;
    private String courseName;
    private String title;
    private Boolean solved;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
}
