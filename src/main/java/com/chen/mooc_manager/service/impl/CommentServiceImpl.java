package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.CommentDao;
import com.chen.mooc_manager.dao.VerifyDao;
import com.chen.mooc_manager.model.Comment;
import com.chen.mooc_manager.model.Verify;
import com.chen.mooc_manager.model.dto.CommentDTO;
import com.chen.mooc_manager.service.CommentService;
import com.chen.mooc_manager.service.VerifyService;
import com.chen.mooc_manager.util.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
    @Resource
    CommentDao commentDao;

    @Resource
    ModelMapper mapper;

    @Autowired
    ParamUtil<Verify> paramUtil;

    /**
    * 添加章节中的评论
     * sectionId：被评论的章节ID
     * userId：发出这条评论的用户ID
     * avatarUrl：发出这条评论的用户头像URL
     * content：评论内容
    * */
    public Boolean addForSection(Integer sectionId,Integer userId,String avatarUrl,String content){
        Comment comment = new Comment();
        comment.setSectionId(sectionId);
        comment.setUserId(userId);
        comment.setAvatarUrl(avatarUrl);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        return commentDao.insert(comment) == 1;
    }

    /**
     * 添加回复评论
     * commentId：要回复的评论ID
     * userId：发出这条评论的用户ID
     * avatarUrl：发出这条评论的用户头像URL
     * content：评论内容
     * */
    public Boolean addForReply(Integer commentId,Integer userId,String avatarUrl,String content){
        Comment comment = new Comment();
        comment.setReplyId(commentId);
        comment.setUserId(userId);
        comment.setAvatarUrl(avatarUrl);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        return commentDao.insert(comment) == 1;
    }

    /**
     * 添加课程评价
     * commentId：被评价的课程ID
     * userId：发出这条评价的用户ID
     * avatarUrl：发出这条评价的用户头像URL
     * content：评价内容
     * */
    public Boolean addForCourse(Integer courseId,Integer userId,String avatarUrl,String content){
        Comment comment = new Comment();
        comment.setCourseId(courseId);
        comment.setUserId(userId);
        comment.setAvatarUrl(avatarUrl);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        return commentDao.insert(comment) == 1;
    }

    public List<CommentDTO> getCommentDto(Integer sectionId){
        List<Comment> comments = commentDao.getBySectionId(sectionId);
        List<CommentDTO> commentDTOs = comments.stream().map(item->{
            CommentDTO commentDTO =  mapper.map(item,CommentDTO.class);
            List<Comment> replies = commentDao.getByReply(item.getId());
            log.info("CommentId:"+item.getId());
            replies.forEach(i->log.info(i.getId()+""));
            commentDTO.setReplies(replies);
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOs;
    }

    @Override
    public List<Comment> getComment(Integer courseId) {
        return commentDao.getByCourseId(courseId);
    }
}
