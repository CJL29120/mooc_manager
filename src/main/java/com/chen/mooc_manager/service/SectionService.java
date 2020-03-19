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

    List<Section> getAllSectionsByPage(Integer startPosition, Integer limit);

    boolean add(Section section);

    boolean edit(Section section);
}
