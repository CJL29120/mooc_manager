package com.chen.mooc_manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mooc_manager.dao.RoleDao;
import com.chen.mooc_manager.dao.UserDao;
import com.chen.mooc_manager.exception.AlreadyExistException;
import com.chen.mooc_manager.exception.MailSendFailedException;
import com.chen.mooc_manager.model.Role;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.model.User;
import com.chen.mooc_manager.service.UserService;
import com.chen.mooc_manager.util.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserDetailsService, UserService {

    @Resource
    UserDao userDao;

    @Resource
    RoleDao roleDao;

    @Resource
    ParamUtil<User> paramUtil;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.selectOne(s);

        List<Role> roles = roleDao.selectList(new QueryWrapper<Role>().lambda().eq(Role::getUserId,user.getId()));
        user.setRoles(roles);

        log.info(user.toString());
         return user;
    }

    @Override
    public boolean recordLogin(Integer userId) {
        User user = userDao.selectById(userId);
        user.setLastLoginTime(new Date());
        return userDao.updateById(user) == 1;
    }

    @Override
    public List<User> getStudentPageByCon(Integer startPosition, Integer limit) {
        return userDao.getStudentPageByCon(startPosition,limit);
    }

    @Override
    public boolean saveAsStudent(User user) throws MailSendFailedException {
        user = paramUtil.afterTrim(user);
        user.setType(1);
        user.setUsername(user.getEmail());
        user.setCreateTime(new Date());
        if(userDao.selectOne(user.getEmail())!=null){
            throw new AlreadyExistException();
        }
        return userDao.insert(user) == 1;
    }

    @Override
    public boolean disbaleUserById(Integer id) {
        return userDao.disbaleUserById(id);
    }

    @Override
    public boolean enbaleUserById(Integer id) {
        return userDao.enbaleUserById(id);
    }


}
