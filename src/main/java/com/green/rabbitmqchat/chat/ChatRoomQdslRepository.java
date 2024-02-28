package com.green.rabbitmqchat.chat;

import com.green.rabbitmqchat.chat.model.ChatRoomVo;
import com.green.rabbitmqchat.chat.model.ChatVo;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomQdslRepository {
    List<ChatRoomVo> selRoomList(long iuser);
    List<ChatVo> selChatList(long ichatRoom, Pageable pageable);
}
