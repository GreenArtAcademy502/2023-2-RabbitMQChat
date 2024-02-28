package com.green.rabbitmqchat.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ChatRoomDto {
    private long otherPersonIuser;
}
