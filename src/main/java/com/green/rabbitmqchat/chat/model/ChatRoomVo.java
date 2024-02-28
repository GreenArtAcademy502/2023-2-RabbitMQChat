package com.green.rabbitmqchat.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomVo {
    private long ichatRoom;
    private String lastMsg;
    private LocalDateTime lastMsgAt;
    private long otherPersonIuser;
    private String otherPersonNm;
    private String otherPersonPic;
}
