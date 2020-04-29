package com.chen.mooc_manager.model.param;

import com.chen.mooc_manager.model.Course;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class CourseAddParam extends Course {
    private int verifyId;
}
