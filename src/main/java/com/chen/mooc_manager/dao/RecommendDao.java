package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Recommend;
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
public interface RecommendDao extends BaseMapper<Recommend> {

    @Select("select id,course_id,weight" +
            " from recommend t " +
            " order by t.weight desc" +
            " limit #{limit}")
    List<Recommend> getRecommends(@Param("limit") Integer limit);

}
