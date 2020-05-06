package com.chen.mooc_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.Section;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
public interface SectionService extends IService<Section> {

    boolean add(Section section);

    boolean edit(Section section);

    Integer getCountById(Integer courseId);

    List<Section> getSectionPageById(Integer offset, Integer limit, Integer courseId);
}
