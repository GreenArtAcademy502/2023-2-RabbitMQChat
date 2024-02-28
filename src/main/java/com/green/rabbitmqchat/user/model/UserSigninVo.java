package com.green.rabbitmqchat.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSigninVo {
    private final int result;
    private long iuser;
    private String nm;
    private String pic;
    private String firebaseToken;
    private String accessToken;
}