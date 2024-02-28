package com.green.rabbitmqchat.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompChatEventListener {

    private final RabbitTemplate template;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){
        log.info("===========");
        log.info("new web socket connection");
        log.info("===========");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        log.info("===========");
        log.info("사용자 퇴장");
        log.info("===========");
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String username=(String)headerAccessor.getSessionAttributes().get("username");
//        if(username != null) {
//            CustomMessage chatMessage = new CustomMessage("Leave",username,"나감");
//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
    }
}
