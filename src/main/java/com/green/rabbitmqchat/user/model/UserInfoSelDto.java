package com.green.rabbitmqchat.user.model;

import lombok.Data;

@Data
public class UserInfoSelDto {
    private int targetIuser;
    private int loginedIuser;
}
