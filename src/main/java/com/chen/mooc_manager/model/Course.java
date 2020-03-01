package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private String classifyId;
    private String classifyName;
    private String shortIntro;
    private String description;
    private String coverUrl;
    private String direction;
    private String creatorId;
    private Integer price;
    private Boolean isSale;
    private Boolean isOnline;
    private Boolean isFinished;
    private Boolean recommend;
    private Integer weight;
    private Integer sectionCount;
    private Integer finishedCount;
    private Integer followerCount;
    private Integer studyCount;
    private Date createTime;
    private Date updateTime;
    private Boolean deleted;

/*主键值，AR模式使用*/
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
