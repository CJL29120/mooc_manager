package com.chen.mooc_manager.base.result;

import lombok.Data;

/**
 * 浏览器向服务端发送的消息用此类接受
 *
 * @author chgl16
 * @date 2018-12-13 16:01
 * @version 1.0
 */

@Data
public class WiselyMessage {
    private String message;
    private Integer userId;
}
