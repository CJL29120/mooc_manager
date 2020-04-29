package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Callback;
import com.chen.mooc_manager.model.Section;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 章节表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Mapper
public interface CallbackDao extends BaseMapper<Callback> {
    @Select(" select count(t.id) from callback t " +
            " where t.resolve = 0 and type = 1")
    Integer getCallbackCount();

    @Select(" select id,user_id,message,create_time from callback t " +
            " where t.resolve = 0 and type = 1 "+
            " order by t.create_time desc " +
            " limit #{startPosition},#{limit}")
    List<Callback> getCallback(@Param("startPosition")Integer startPosition,@Param("limit")Integer limit);


    @Update("update callback t set t.resolve = 1 where t.id = #{id}")
    Boolean updateCallbackStatus(@Param("id")Integer id);

    @Select(" select id,user_id,message,create_time from callback t " +
            " where t.resolve = 0 and type = 3 "+
            " order by t.create_time desc ")
    List<Callback> getNotice();

}
