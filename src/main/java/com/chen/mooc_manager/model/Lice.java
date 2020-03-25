package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class Lice extends Model<Lice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer commentId;
    private Integer userId;
    private Date createTime;
    private Date updateTime;
    private Boolean deleted;

    /*主键值，AR模式使用*/
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
