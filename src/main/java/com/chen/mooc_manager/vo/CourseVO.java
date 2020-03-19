package com.chen.mooc_manager.vo;

import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.CourseComment;
import com.chen.mooc_manager.model.Section;
import com.chen.mooc_manager.model.Teacher;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class CourseVO extends Course {
    private List<Section> sections;
    private List<CourseComment> comments;
    private Teacher creator;

    public void setCreator(Teacher creator) {
        this.creator = creator;
    }
    public Teacher getCreator() {
        return creator;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
    public List<Section> getSections() {
        return sections;
    }
}
