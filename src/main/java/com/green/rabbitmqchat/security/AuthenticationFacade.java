package com.green.rabbitmqchat.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public MyUserDetails getLoginUser() {
        return (MyUserDetails) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();
    }

    public long getLoginUserPk() {
        MyUserDetails myUserDetails = getLoginUser();
        return myUserDetails == null
                ? 0
                : myUserDetails
                    .getMyPrincipal()
                    .getIuser();
    }
}
