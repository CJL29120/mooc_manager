package com.chen.mooc_manager.exception;

public class ParamWrongException extends RuntimeException{

    private Integer code = 500;

    public ParamWrongException(String message){
        super(message);
    }

    public ParamWrongException(String message, int code){
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
