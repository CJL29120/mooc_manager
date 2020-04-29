package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface UserDao extends BaseMapper<User> {



    /*User*/
    @Select(" select id,username,password,gender,avatar_url,email,status,birthday, " +
            " education,sign,province,city,last_login_time,deleted " +
            " from user t " +
            " where t.username = #{nameOrEmail} or t.email = #{nameOrEmail} ")
    User selectOne(@Param("nameOrEmail")String nameOrEmail);

    @Update(" update user u set u.status = 3 where u.id = #{id} ")
    Boolean disbaleUserById(@Param("id")Integer id);

    @Update(" update user u set u.status = 1 where u.id = #{id} ")
    Boolean enbaleUserById(@Param("id")Integer id);

    /*Student*/
    @Select("select id,username,gender,avatar_url,email,birthday,education,college_name,major,sign,status " +
            "from user t " +
            "order by t.id" +
            " limit #{startPosition},#{limit}")
    List<User> getStudentPageByCon(Integer startPosition, Integer limit);



    /*Teacher*/
    @Select(" select id,username,gender,avatar_url,email,status,birthday, " +
            " education,sign,province,city,last_login_time,deleted,short_intro,courses_count " +
            " from user t " +
            " where t.id = #{teacherId} and t.type = 2 ")
    User getTeacherById(@Param("teacherId")Integer teacherId);

    @Update(" update user u set u.courses_count=u.courses_count+1 where u.id = #{creatorId} ")
    boolean addCourseCountByUserId(@Param("creatorId")int creatorId);

    @Update(" update user u set u.courses_count=u.courses_count-#{delCount} where u.id = #{creatorId} ")
    boolean decrCourseCountByUserId(@Param("delCount")Integer delCount,@Param("creatorId") Integer creatorId);
}
