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
 * 
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String permission;
    private Integer parentId;
    private Integer sort;
    private String css;
    private String href;
    private Boolean type;
    private Date createTime;
    private Date updateTime;
    private Boolean deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
