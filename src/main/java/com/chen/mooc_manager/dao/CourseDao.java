package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Mapper
public interface CourseDao extends BaseMapper<Course> {

    @Select(" select id,name,type,classify_name,short_intro,cover_url,direction,price, " +
            " is_online,is_finished,section_count,follower_count,study_count,end_time  " +
            " from course t " +
            " order by t.create_time " +
            " limit #{startPosition},#{limit}")
    List<Course> getAllCoursesByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);


    @Select(" select id,name,type,classify_name,short_intro,cover_url,direction,price, " +
            " is_online,is_finished,section_count,follower_count,study_count,end_time  " +
            " from course t " +
            " where t.type=2 " +
            " order by t.create_time " +
            " limit #{limit}")
    List<Course> getVipCourses(@Param("limit")Integer limit);


    @Select(" select count(t.id) " +
            " from course t " +
            " where t.creator_id = #{creatorId}")
    Integer getCountById(@Param("creatorId")Integer creatorId);


    @Select(" select id,name,type,classify_name,short_intro,cover_url,direction,price, " +
            " is_online,is_finished,section_count,follower_count,study_count,end_time  " +
            " from course t " +
            " where t.creator_id = #{creatorId} " +
            " order by t.create_time " +
            " limit #{startPosition},#{limit}")
    List<Course> getCoursePageById(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit,@Param("creatorId")Integer creatorId);


    Integer countWithCondition(@Param("map")Map map);

    List<Course> selectWithCondition(@Param("map")Map map, @Param("orderBy")String orderBy, @Param("orderDirection")String orderDirection,
                                     @Param("offset")Integer offset,@Param("limit")Integer limit);


}
