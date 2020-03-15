package com.chen.mooc_manager.vo;

import com.chen.mooc_manager.model.CourseComment;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class CourseCommentVO extends CourseComment{
    List<CourseComment> flw_comments;

    public List<CourseComment> getFlw_comments() {
        return flw_comments;
    }

    public void setFlw_comments(List<CourseComment> flw_comments) {
        this.flw_comments = flw_comments;
    }
}
