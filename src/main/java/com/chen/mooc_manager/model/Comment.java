package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程评论&答疑
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Data
public class Comment{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String avatarUrl;
    private Integer replyId;
    private Integer courseId;
    private Integer sectionId;
    private Boolean like;
    private String content;
    private Date createTime;
    private Date updateTime;
    private Boolean deleted;

}
