package com.green.rabbitmqchat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_chatroom_msg")
public class ChatRoomMsgEntity extends CreatedAtEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED", name = "ichat_msg")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ichatMsg;

    @ManyToOne
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name = "ichat_room")
    private ChatRoomEntity chatRoomEntity;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @Column(length = 2000, nullable = false)
    private String msg;
}
