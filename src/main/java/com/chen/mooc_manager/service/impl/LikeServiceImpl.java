package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.LikeDao;
import com.chen.mooc_manager.model.Lice;
import com.chen.mooc_manager.service.LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeDao, Lice> implements LikeService {

    @Resource
    LikeDao likeDao;

    @Override
    public Boolean save(Integer commentId, Integer userId) {
        Lice like = new Lice();
        like.setCommentId(commentId);
        like.setUserId(userId);
        return likeDao.insert(like) == 1;
    }
}
