package com.green.rabbitmqchat.common;

import com.green.rabbitmqchat.exception.StompErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // 메세지 브로커를 사용해서 메세지 핸들링이 가능
public class StompConfiguration implements WebSocketMessageBrokerConfigurer {

    private final StompPreHandler stompPreHandler;
    private final StompErrorHandler stompErrorHandler;
    private final RabbitMQProperties properties;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.setErrorHandler(stompErrorHandler)
                .addEndpoint(properties.getStompEndPoint())
                .setAllowedOriginPatterns(properties.getStompAllowedOriginPatterns())
                .withSockJS();
    }

    @Override //메세지 브로커를 설정
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.setPathMatcher(new AntPathMatcher("."))// url을 chat/room/3 -> chat.room.3으로 참조하기 위한 설정
        .setApplicationDestinationPrefixes(properties.getStompApplicationDestinationPrefixes()) //메시지 발행 요청 url
                .enableStompBrokerRelay(properties.getStompBrokerRelay())
                .setRelayHost(properties.getHost())
                .setRelayPort(properties.getStompRelayPort())
                .setVirtualHost(properties.getVirtualHost())
                .setClientLogin(properties.getUsername())
                .setClientPasscode(properties.getPassword())
                .setSystemLogin(properties.getUsername())
                .setSystemPasscode(properties.getPassword());

    }

    @Override //tcp handshake시 jwt 인증용
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompPreHandler);
    }
}
