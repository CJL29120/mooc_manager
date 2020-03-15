package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class Verify extends Model<Verify> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String shortIntro;
    private String creatorId;
    private Integer status;
    private Integer sectionCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    private Date createTime;
    private Date updateTime;
    private Boolean deleted;

    /*主键值，AR模式使用*/
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
