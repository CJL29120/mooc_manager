package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.CourseCommentDao;
import com.chen.mooc_manager.model.CourseComment;
import com.chen.mooc_manager.service.CourseCommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程评论&答疑 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Service
public class CourseCommentServiceImpl extends ServiceImpl<CourseCommentDao, CourseComment> implements CourseCommentService {

}
