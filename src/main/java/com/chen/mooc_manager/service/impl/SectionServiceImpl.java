package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.SectionDao;
import com.chen.mooc_manager.model.Section;
import com.chen.mooc_manager.service.SectionService;
import com.chen.mooc_manager.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    SectionDao sectionDao;

    @Override
    public boolean add(Section section) {
        section.setCreateTime(new Date());
        section.setUploadTime(new Date());
        int lastSort =  sectionDao.getMaxSort(section.getCourseId())== null?0:sectionDao.getMaxSort(section.getCourseId());
        section.setSort(lastSort+1);
        return super.save(section);
    }

    @Override
    public boolean edit(Section section) {
        Section old = sectionDao.selectById(section.getId());

        if(!old.getVideoUrl().equals(section.getVideoUrl())){
            section.setUploadTime(new Date());
        }
        return super.updateById(section);
    }

    @Override
    public Integer getCountById(Integer courseId) {
        return sectionDao.getCountById(courseId);
    }

    @Override
    public List<Section> getSectionPageById(Integer offset, Integer limit, Integer courseId) {
        return sectionDao.getSectionPageById(offset,limit,courseId);
    }
}
