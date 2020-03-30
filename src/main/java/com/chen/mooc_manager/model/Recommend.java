package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
public class Recommend{
    private Integer id;
    private Integer courseId;
    private Integer weight;

}
