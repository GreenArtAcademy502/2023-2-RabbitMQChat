package com.green.rabbitmqchat.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;

    // valids가 null이 아니고  size 1이상 이라면 JSON으로 만들어진다. 아니면 안 만들어진다.
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> valids;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {
        // @Valid 로 에러가 들어왔을 때, 어느 필드에서 에러가 발생했는 지에 대한 응답 처리
        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
