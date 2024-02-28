package com.green.rabbitmqchat.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {
    ERR_OCCURRED(HttpStatus.NOT_FOUND, "에러가 발생하였습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    NOT_EXIST_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
