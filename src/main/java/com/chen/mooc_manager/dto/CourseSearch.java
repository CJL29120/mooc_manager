package com.chen.mooc_manager.dto;

import com.chen.mooc_manager.model.Course;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class CourseSearch extends Course {
    private int order;
    private int current;
}
