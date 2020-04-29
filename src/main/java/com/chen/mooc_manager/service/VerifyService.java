package com.chen.mooc_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mooc_manager.model.Verify;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
public interface VerifyService extends IService<Verify> {

    boolean save(Verify entity);

    List<Verify> getUnhandledByPage(Integer startPosition, Integer limit);

    Integer getUnhandledCount();

    Boolean enableById(Integer id);

    Boolean disableById(Integer id);

    Integer getCountByCreator(Integer creatorId);

    List<Verify> getPageByCreator(Integer offset, Integer limit,Integer creatorId);
}
