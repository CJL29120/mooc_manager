package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.VerifyDao;
import com.chen.mooc_manager.model.Verify;
import com.chen.mooc_manager.service.VerifyService;
import com.chen.mooc_manager.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class VerifyServiceImpl extends ServiceImpl<VerifyDao, Verify> implements VerifyService {
    @Resource
    VerifyDao verifyDao;

    @Autowired
    ParamUtil<Verify> paramUtil;

    @Override
    public boolean save(Verify entity) {
        entity = paramUtil.afterTrim(entity);
        return verifyDao.save(entity);
    }

    @Override
    public List<Verify> getUnhandledByPage(Integer startPosition, Integer limit) {
        return verifyDao.getUnhandledByPage(startPosition,limit);
    }

    @Override
    public Integer getUnhandledCount() {
        return verifyDao.getUnhandledCount();
    }

    @Override
    public Boolean enableById(Integer id) {
        return verifyDao.enableById(id);
    }

    @Override
    public Boolean disableById(Integer id) {
        return verifyDao.disableById(id);
    }

    @Override
    public Integer getCountByCreator(Integer creatorId) {
        return verifyDao.getCountByCreatorId(creatorId);
    }

    @Override
    public List<Verify> getPageByCreator(Integer offset, Integer limit, Integer creatorId) {
        return verifyDao.getPageByCreatorId(offset,limit,creatorId);
    }
}
