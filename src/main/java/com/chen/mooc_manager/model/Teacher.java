package com.chen.mooc_manager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 教师表
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Data
@ToString(callSuper = true)
public class Teacher extends User{

    private static final long serialVersionUID = 1L;

    private String mobile;
    private int coursesCount;
    private String shortIntro;
}
