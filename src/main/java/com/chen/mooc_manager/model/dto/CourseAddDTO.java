package com.chen.mooc_manager.model.dto;

import com.chen.mooc_manager.model.Course;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class CourseAddDTO extends Course {
    private int verifyId;
}
