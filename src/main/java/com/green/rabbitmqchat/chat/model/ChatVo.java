package com.green.rabbitmqchat.chat.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatVo {
    private long ichatMsg;
    private long iuser;
    private String userNm;
    private String msg;
    private LocalDateTime createdAt;
}
