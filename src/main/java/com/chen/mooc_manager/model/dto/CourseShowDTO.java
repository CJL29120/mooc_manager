package com.chen.mooc_manager.model.dto;

import com.chen.mooc_manager.model.Course;
import com.chen.mooc_manager.model.Section;
import com.chen.mooc_manager.model.Teacher;
import com.chen.mooc_manager.model.User;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class CourseShowDTO extends Course {
    private List<Section> sections;
    private User creator;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
