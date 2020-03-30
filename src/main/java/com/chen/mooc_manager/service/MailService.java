package com.chen.mooc_manager.service;

public interface MailService {

    void sendTextMail(String toAddr, String title, String content);
}
