package com.green.rabbitmqchat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
//@Component
public class StompErrorHandlerTemp extends StompSubProtocolErrorHandler {
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        if(ex.getCause().getMessage().equals("JWT")){
            return handleJwtException(clientMessage, ex);
        }

        if(ex.getCause().getMessage().equals("Auth")){
            return handleUnauthorizedException(clientMessage, ex);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> handleUnauthorizedException(Message<byte[]> clientMessage, Throwable ex) {
        //ApiError apiError = new ApiError(ex.getMessage());
        //return prepareErrorMessage(clientMessage, apiError, "권한이 없습니다.");
        return null;
    }

    // JWT 예외
    private Message<byte[]> handleJwtException(Message<byte[]> clientMessage, Throwable ex){

        //return prepareErrorMessage(JwtErrorCode.ACCESS_TOKEN_EXPIRATION);
        return null;
    }

    // 메세지 생성
    private Message<byte[]> prepareErrorMessage(ErrorCode responseCode)
    {

        String code = String.valueOf(responseCode.getMessage());

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

       // accessor.setMessage(String.valueOf(responseCode.getCode()));
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(code.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
