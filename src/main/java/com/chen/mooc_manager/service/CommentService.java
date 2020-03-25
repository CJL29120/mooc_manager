package com.chen.mooc_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Comment;
import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.dto.CommentDTO;
import com.chen.mooc_manager.model.dto.CourseShowDTO;
import com.chen.mooc_manager.model.param.CourseConditionParam;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
public interface CommentService extends IService<Comment> {

    Boolean addForSection(Integer sectionId,Integer userId,String avatarUrl,String content);

    Boolean addForReply(Integer commentId,Integer userId,String avatarUrl,String content);

    Boolean addForCourse(Integer courseId,Integer userId,String avatarUrl,String content);

    List<CommentDTO> getCommentDto(Integer sectionId);

    List<Comment> getComment(Integer courseId);
}
