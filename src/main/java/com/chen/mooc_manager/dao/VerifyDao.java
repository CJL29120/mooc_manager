package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Verify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Mapper
public interface VerifyDao extends BaseMapper<Verify> {

    @Select("select id,name,status,section_count,short_intro " +
            " from verify t " +
            " order by t.status desc" +
            " limit #{startPosition},#{limit}")
    List<Verify> getAllChecksByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    boolean save(Verify verify);
}
