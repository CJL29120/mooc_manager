package com.chen.mooc_manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.mooc_manager.model.Section;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface SectionDao extends BaseMapper<Section> {

    @Select("select id,section_id,name,cover_url,video_url,upload_time " +
            " from section t " +
            " order by t.create_time " +
            " limit #{startPosition},#{limit}")
    List<Section> getAllSectionsByPage(@Param("startPosition") Integer startPosition, @Param("limit") Integer limit);
}
