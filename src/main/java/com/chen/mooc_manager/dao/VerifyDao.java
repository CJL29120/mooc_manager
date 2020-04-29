package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Verify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    boolean save(Verify verify);

    @Select(" select id,name,section_count,short_intro,create_time " +
            " from verify t " +
            " where t.status = 0 " +
            " order by t.create_time desc" +
            " limit #{startPosition},#{limit}")
    List<Verify> getUnhandledByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    @Select("select count(t.id) " +
            " from verify t " +
            " where t.status = 0")
    Integer getUnhandledCount();

    @Select(" select id,name,status,section_count,short_intro " +
            " from verify t " +
            " where t.creator_id = #{creatorId} " +
            " order by t.status desc " +
            " limit #{startPosition},#{limit} ")
    List<Verify> getPageByCreatorId(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit,@Param("creatorId")Integer id);


    @Select("select count(t.id) " +
            " from verify t " +
            " where t.creator_id = #{creatorId}")
    Integer getCountByCreatorId(@Param("creatorId")Integer id);

    @Update("update verify t set t.status = 1 where t.id = #{id}")
    Boolean enableById(@Param("id")Integer id);

    @Update("update verify t set t.status = -1 where t.id = #{id}")
    Boolean disableById(@Param("id")Integer id);
}
