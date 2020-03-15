package com.chen.mooc_manager.model.param;

import lombok.Data;

import java.util.Date;

@Data
public class StudentParam {

    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private Boolean gender;
    private String avatarUrl;
    private String email;
    private Date birthday;
    private String education;
    private String collegeName;
    private String major;
    private String sign;

}
