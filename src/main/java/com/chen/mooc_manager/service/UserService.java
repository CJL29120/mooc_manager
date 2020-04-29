package com.chen.mooc_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.mooc_manager.exception.MailSendFailedException;
import com.chen.mooc_manager.model.Student;
import com.chen.mooc_manager.model.User;
import com.chen.mooc_manager.model.Verify;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-02-29
 */
public interface UserService extends IService<User> {
    boolean recordLogin(Integer userId);

    List<User> getStudentPageByCon(Integer startPosition,Integer limit);

    boolean saveAsStudent(User user) throws MailSendFailedException;

    boolean disbaleUserById(Integer id);

    boolean enbaleUserById(Integer id);

    boolean addCourseCountByUserId(int creatorId);

    boolean decrCourseCountByUserId(Integer delCount, Integer creatorId);
}
