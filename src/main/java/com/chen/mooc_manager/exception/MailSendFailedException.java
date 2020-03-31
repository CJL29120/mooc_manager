package com.chen.mooc_manager.exception;

public class MailSendFailedException extends RuntimeException{

    private Integer code = 500;

    public MailSendFailedException(){
        super("邮件发送失败！");
    }

    public MailSendFailedException(int code){
        super("邮件发送失败！");
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
