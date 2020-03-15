package com.chen.mooc_manager.vo;

import com.chen.mooc_manager.model.CourseComment;
import com.chen.mooc_manager.model.CourseSection;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class CourseSectionVO extends CourseSection {
    private List<CourseComment> comments;
}
