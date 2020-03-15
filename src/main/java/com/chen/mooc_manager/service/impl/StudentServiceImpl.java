package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.StudentDao;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

    @Autowired
    StudentDao studentDao;

    @Override
    public boolean save(Student entity) {
        Assert.isNull(studentDao.selectOne(new QueryWrapper<Student>().lambda()
                .eq(Student::getEmail,entity.getEmail())),
                "该邮箱账号已经注册！");

        Assert.isNull(studentDao.selectOne(new QueryWrapper<Student>().lambda()
                        .eq(Student::getUsername,entity.getUsername())),
                "该用户名已经注册！");


        return studentDao.save(entity);
    }

    @Override
    public boolean updateById(Student entity) {
        Assert.notNull(entity.getId(),"请选择更新的用户");
        return super.updateById(entity);
    }

    public List<Student> getAllUsersByPage(Integer startPosition,Integer limit){
        return studentDao.getAllUsersByPage(startPosition, limit);
    }
}
