package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.VerifyDao;
import com.chen.mooc_manager.model.Verify;
import com.chen.mooc_manager.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    VerifyDao verifyDao;

    @Override
    public boolean save(Verify entity) {
        return verifyDao.save(entity);
    }

    @Override
    public List<Verify> getAllChecksByPage(Integer startPosition, Integer limit) {
        return verifyDao.getAllChecksByPage(startPosition,limit);
    }
}
