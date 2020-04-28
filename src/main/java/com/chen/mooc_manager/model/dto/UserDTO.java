package com.chen.mooc_manager.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class UserDTO{

    private Integer id;
    private String username;
    private Boolean gender;
    private String avatarUrl;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String sign;
    private String courseName;
    private String major;
    private Integer status;

}
