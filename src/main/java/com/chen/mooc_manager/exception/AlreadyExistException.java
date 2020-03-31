package com.chen.mooc_manager.exception;

public class AlreadyExistException extends RuntimeException{
    private Integer code = 500;

    public AlreadyExistException(){
        super("用户已存在！");
    }

    AlreadyExistException(int code){
        super("用户已存在！");
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
