package com.green.rabbitmqchat.chat;

import com.green.rabbitmqchat.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>, ChatRoomQdslRepository {
}
