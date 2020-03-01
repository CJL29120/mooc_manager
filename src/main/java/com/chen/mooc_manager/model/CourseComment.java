package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程评论&答疑
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseComment extends Model<CourseComment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String toUsername;
    private Integer courseId;
    private Integer sectionId;
    private String sectionTitle;
    private String content;
    private Integer refId;
    private String refContent;
    private Integer type;
    private Date createTime;
    private Date updateTime;
    private Boolean deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
