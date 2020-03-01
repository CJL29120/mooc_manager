package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.UserCollectionsDao;
import com.chen.mooc_manager.model.UserCollections;
import com.chen.mooc_manager.service.UserCollectionsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Service
public class UserCollectionsServiceImpl extends ServiceImpl<UserCollectionsDao, UserCollections> implements UserCollectionsService {

}
