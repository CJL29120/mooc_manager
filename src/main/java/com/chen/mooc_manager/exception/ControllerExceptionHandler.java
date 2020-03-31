package com.chen.mooc_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleUserAlreadyExist(AlreadyExistException e){
        Map<String,Object> map = new HashMap<>();
        map.put("code",e.getCode());
        map.put("message",e.getMessage());
        return map;
    }

    @ExceptionHandler(MailSendFailedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleMailSendFailed(MailSendFailedException e){
        Map<String,Object> map = new HashMap<>();
        map.put("code",e.getCode());
        map.put("message",e.getMessage());
        return map;
    }

    @ExceptionHandler(ParamWrongException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleParamWrong(ParamWrongException e){
        Map<String,Object> map = new HashMap<>();
        map.put("code",e.getCode());
        map.put("message",e.getMessage());
        return map;
    }
}
