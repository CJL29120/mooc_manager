package com.chen.mooc_manager.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Data
public class Student{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private Boolean gender;
    private String avatarUrl="";
    private String email="";
    private Boolean status=false;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String education="";
    private String collegeName="";
    private String major="";
    private String sign="";
    private String province="";
    private String city="";
    private String ip="";
    private Date createTime;
    private Date updateTime;
    private Boolean deleted=false;

}
