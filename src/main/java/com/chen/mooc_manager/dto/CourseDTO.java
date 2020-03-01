package com.chen.mooc_manager.dto;

import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.CourseComment;
import com.chen.mooc_manager.model.CourseSection;
import com.chen.mooc_manager.model.Teacher;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class CourseDTO extends Course {
    private List<CourseSection> sections;
    private List<CourseComment> comments;
    private Teacher creator;

    public void setCreator(Teacher creator) {
        this.creator = creator;
    }
    public Teacher getCreator() {
        return creator;
    }

    public void setSections(List<CourseSection> sections) {
        this.sections = sections;
    }
    public List<CourseSection> getSections() {
        return sections;
    }
}
