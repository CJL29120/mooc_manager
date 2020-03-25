package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.Comment;
import com.chen.mooc_manager.model.Lice;
import com.chen.mooc_manager.model.dto.CommentDTO;
import com.chen.mooc_manager.service.CommentService;
import com.chen.mooc_manager.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @PostMapping(value="/addSectionComment")
    @ResponseBody
    public Results<Comment> addSectionComment(@RequestParam(value = "sectionId",defaultValue = "2",required = false)Integer sectionId, @RequestParam(value = "userId",defaultValue = "2",required = false)Integer userId,
                                              @RequestParam(value = "avatarUrl",defaultValue = "../img/ncn1.jpg", required = false)String avatarUrl, @RequestParam(value = "content",required = false)String content){
        return commentService.addForSection(sectionId, userId, avatarUrl, content)?Results.success():Results.failure();
    }

    @PostMapping(value="/addReplyComment")
    @ResponseBody
    public Results<Comment> addReplyComment(@RequestParam(value = "commentId",defaultValue = "2",required = false)Integer commentId, @RequestParam(value = "userId",defaultValue = "2",required = false)Integer userId,
                                              @RequestParam(value = "avatarUrl",defaultValue = "../img/ncn1.jpg", required = false)String avatarUrl, @RequestParam(value = "content",required = false)String content){
        return commentService.addForReply(commentId, userId, avatarUrl, content)?Results.success():Results.failure();
    }


    @GetMapping(value="/sectionComment")
    @ResponseBody
    public Results<CommentDTO> sectionComment(@RequestParam(value = "sectionId",defaultValue = "2",required = false)Integer sectionId){
        return Results.success(commentService.getCommentDto(sectionId));
    }

    @GetMapping(value="/courseComment")
    @ResponseBody
    public Results<Comment> courseComment(@RequestParam(value = "courseId",defaultValue = "2",required = false)Integer courseId){
        return Results.success(commentService.getComment(courseId));
    }

    @PostMapping("like")
    @ResponseBody
    public Results<Boolean> addLike(@RequestParam(value = "commentId")Integer commentId,@RequestParam(value = "userId")Integer userId){
        return likeService.save(commentId,userId)?Results.success():Results.failure();
    }

    @GetMapping("like")
    @ResponseBody
    public Results<Lice> getLike(){
        return Results.success(likeService.list());
    }
}
