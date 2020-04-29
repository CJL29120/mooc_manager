package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 课程章节
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Data
public class Callback {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer type;
    private String message;
    private Boolean resolve;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
}
