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
 * 用户收藏
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Data
public class UserCollections{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer classify;
    private Integer objectId;
    private String tips;
    private Date createTime;
    private Date updateTime;
    private Boolean deleted;

}
