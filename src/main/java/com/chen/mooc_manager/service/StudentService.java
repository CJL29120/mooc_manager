package com.chen.mooc_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mooc_manager.model.Student;

import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
public interface StudentService extends IService<Student> {
    List<Student> getAllUsersByPage(Integer startPosition,Integer limit);

    boolean save(Student student);
}
