package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.StudentDao;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

}
