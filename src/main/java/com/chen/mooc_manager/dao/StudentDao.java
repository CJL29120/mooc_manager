package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Mapper
public interface StudentDao extends BaseMapper<Student> {

    @Select("select id,username,nickname,gender,avatar_url,email,birthday,education,college_name,major,sign " +
            "from student t " +
            " order by t.id" +
            " limit #{startPosition},#{limit}")
    List<Student> getAllUsersByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    boolean save(Student student);
}
