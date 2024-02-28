package com.green.rabbitmqchat.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.rabbitmqchat.common.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "회원가입 데이터")
public class UserSignupDto {
    @JsonIgnore
    private Long iuser;

    @Schema(title = "유저 아이디", example = "mic")
    private String uid;

    @Schema(title = "유저 비밀번호", example = "1212")
    private String upw;

    @Schema(title = "이름", example = "홍길동")
    private String nm;

    private String pic;

    @JsonIgnore
    private RoleEnum role;
}
