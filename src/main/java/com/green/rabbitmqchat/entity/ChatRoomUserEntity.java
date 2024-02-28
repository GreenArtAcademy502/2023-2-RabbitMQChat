package com.green.rabbitmqchat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_chatroom_user")
public class ChatRoomUserEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED", name="ichat_room_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ichatRoomUser;

    @ManyToOne
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name = "ichat_room", nullable = false)
    private ChatRoomEntity chatRoomEntity;

    @ManyToOne
    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name = "iuser", nullable = false)
    private UserEntity userEntity;
}
