package com.green.rabbitmqchat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_chatroom")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEntity extends CreatedAtEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ichatRoom;

    @Column(length = 2000)
    private String lastMsg;

    @LastModifiedDate
    @Column(name = "last_msg_at")
    private LocalDateTime lastMsgAt;

    @ToString.Exclude
    @OneToMany(mappedBy = "chatRoomEntity", cascade = CascadeType.PERSIST)
    private List<ChatRoomUserEntity> chatRoomUserList = new ArrayList();

    @ToString.Exclude
    @OneToMany(mappedBy = "chatRoomEntity", cascade = CascadeType.PERSIST)
    private List<ChatRoomMsgEntity> chatRoomMsgList = new ArrayList();
}
