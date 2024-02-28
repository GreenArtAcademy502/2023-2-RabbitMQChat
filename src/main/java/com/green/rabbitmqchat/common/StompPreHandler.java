package com.green.rabbitmqchat.common;

import com.green.rabbitmqchat.exception.AuthErrorCode;
import com.green.rabbitmqchat.exception.ChatErrorCode;
import com.green.rabbitmqchat.exception.RestApiException;
import com.green.rabbitmqchat.security.JwtTokenProvider;
import com.green.rabbitmqchat.security.MyPrincipal;
import com.green.rabbitmqchat.security.MyUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * Stomp의 JWT 검증처리
 *
 *
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class StompPreHandler implements ChannelInterceptor {
    private final AppProperties appProperties;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(StompCommand.CONNECT.equals(accessor.getCommand())) {
            //헤더 토큰 얻기
            String authorizationHeader = accessor.getNativeHeader(appProperties.getJwt().getHeaderSchemeName()) == null ? null : String.valueOf(accessor.getNativeHeader(appProperties.getJwt().getHeaderSchemeName()).get(0));
            if(authorizationHeader == null || authorizationHeader.equals("null")) {
                //throw new MessageDeliveryException("토큰이 존재하지 않습니다.");
                throw new RestApiException(ChatErrorCode.NOT_EXIST_TOKEN);
            }

            String token = authorizationHeader.substring(appProperties.getJwt().getTokenType().length() + 1);
            if(token == null || !jwtTokenProvider.isValidateToken(token)) {
                throw new RestApiException(ChatErrorCode.UNAUTHORIZED);
            }
            log.info("command = {}",accessor.getCommand());
            log.info("token = {}", token);
        }
        return message;
    }
}
