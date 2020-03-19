package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.SectionDao;
import com.chen.mooc_manager.model.Section;
import com.chen.mooc_manager.service.SectionService;
import com.chen.mooc_manager.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Service
public class SectionServiceImpl extends ServiceImpl<SectionDao, Section> implements SectionService {

    @Autowired
    SectionDao sectionDao;

    @Autowired
    ParamUtil<Section> paramUtil;

    @Override
    public List<Section> getAllSectionsByPage(Integer startPosition, Integer limit) {
        return sectionDao.getAllSectionsByPage(startPosition, limit);
    }

    @Override
    public boolean add(Section section) {
        section = paramUtil.afterTrim(section);
        section.setUploadTime(new Date());
        return super.save(section);
    }

    @Override
    public boolean edit(Section section) {
        section = paramUtil.afterTrim(section);
        Section old = sectionDao.selectById(section.getId());

        if(!old.getVideoUrl().equals(section.getVideoUrl())){
            section.setUploadTime(new Date());
        }
        return super.updateById(section);
    }
}
