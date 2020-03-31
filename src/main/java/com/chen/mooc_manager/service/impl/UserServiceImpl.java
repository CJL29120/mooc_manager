package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.mooc_manager.dao.RoleDao;
import com.chen.mooc_manager.dao.StudentDao;
import com.chen.mooc_manager.dao.TeacherDao;
import com.chen.mooc_manager.model.Role;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.model.Teacher;
import com.chen.mooc_manager.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService {

    @Resource
    StudentDao studentDao;

    @Resource
    TeacherDao teacherDao;

    @Resource
    RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = studentDao.selectOne(new QueryWrapper<Student>().lambda().eq(Student::getEmail,s));
        if(user == null){
            user = teacherDao.selectOne(new QueryWrapper<Teacher>().lambda().eq(Teacher::getEmail,s));
        }
        user = user==null ? new User() : user;

        List<Role> roles = roleDao.selectList(new QueryWrapper<Role>().lambda().eq(Role::getUserId,user.getId()));
        user.setRoles(roles);

        log.info(user.toString());
         return user;
    }
}
