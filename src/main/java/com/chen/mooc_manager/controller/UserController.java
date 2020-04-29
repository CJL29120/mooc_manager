package com.chen.mooc_manager.controller;

import com.chen.mooc_manager.base.result.PageTableRequest;
import com.chen.mooc_manager.base.result.Results;
import com.chen.mooc_manager.model.User;
import com.chen.mooc_manager.model.dto.UserDTO;
import com.chen.mooc_manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/loadInfo")
    @ResponseBody
    public Results loadInfo(Integer userId){
        User user= userService.getById(userId);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO,UserDTO.class);
        return Results.success(userDTO);
    }

    String pattern = "yyyy-MM-dd";

    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));// CustomDateEditor为自定义日期编辑器
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public Results updateInfo(User user){
        return userService.updateById(user) ? Results.success():Results.failure();
    }



    /*后台管理*/
    @GetMapping("/list")
    @ResponseBody
    public Results<UserDTO> list(PageTableRequest request){
        Assert.notNull(request,"请求显示的页码参数不能为空");
        List<User> users = userService.getStudentPageByCon(request.getOffset(),request.getLimit());
        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO,UserDTO.class);
            return userDTO;
        }).collect(Collectors.toList());
        return Results.success(userService.count(),userDTOs);
    }

    @PostMapping("/disable")
    @ResponseBody
    public Results disable(Integer id){
       return userService.disbaleUserById(id)?Results.success():Results.failure();
    }

    @PostMapping("/enable")
    @ResponseBody
    public Results enable(Integer id){
        return userService.enbaleUserById(id)?Results.success():Results.failure();
    }
}
