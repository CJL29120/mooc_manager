package com.chen.mooc_manager.model.dto;

import com.chen.mooc_manager.model.Comment;
import com.chen.mooc_manager.model.Course;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class CommentDTO extends Comment {
    private List<Comment> replies;

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
}
