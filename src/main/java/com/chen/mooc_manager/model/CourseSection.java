package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = true)
public class CourseSection extends Model<CourseSection> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer courseId;
    private String name;
    private Integer sort;
    private Integer duration;
    private Boolean isOnline;
    private Date createTime;
    private Date updateTime;
    private Boolean deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
