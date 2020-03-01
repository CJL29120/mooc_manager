package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Teacher;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 教师表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Mapper
public interface TeacherDao extends BaseMapper<Teacher> {

}
