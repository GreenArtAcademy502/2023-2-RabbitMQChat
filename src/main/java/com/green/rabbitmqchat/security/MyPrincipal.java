package com.green.rabbitmqchat.security;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPrincipal {
    private long iuser;
    private String nm;

    @Builder.Default
    private List<String> roles = new ArrayList();
}
