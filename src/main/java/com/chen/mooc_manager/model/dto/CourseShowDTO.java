package com.chen.mooc_manager.model.dto;

import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.Section;
import com.chen.mooc_manager.model.Teacher;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class CourseShowDTO extends Course {
    private List<Section> sections;
    private Teacher creator;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Teacher getCreator() {
        return creator;
    }

    public void setCreator(Teacher creator) {
        this.creator = creator;
    }
}
