package com.green.rabbitmqchat.chat;

import com.green.rabbitmqchat.chat.ChatRoomQdslRepository;
import com.green.rabbitmqchat.chat.model.ChatRoomVo;
import com.green.rabbitmqchat.chat.model.ChatVo;
import com.green.rabbitmqchat.entity.QChatRoomUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.green.rabbitmqchat.entity.QChatRoomEntity.chatRoomEntity;
import static com.green.rabbitmqchat.entity.QChatRoomMsgEntity.chatRoomMsgEntity;
import static com.green.rabbitmqchat.entity.QChatRoomUserEntity.chatRoomUserEntity;
import static com.green.rabbitmqchat.entity.QUserEntity.userEntity;

@Slf4j
@RequiredArgsConstructor
public class ChatRoomQdslRepositoryImpl implements ChatRoomQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatRoomVo> selRoomList(long loginIuser) {
        QChatRoomUserEntity chatRoomUserEntity2 = new QChatRoomUserEntity("chat_room_user_2");
        return jpaQueryFactory.select(Projections.bean(ChatRoomVo.class
                , chatRoomEntity.ichatRoom
                        , chatRoomEntity.lastMsg
                        , chatRoomEntity.lastMsgAt
                        , chatRoomUserEntity2.userEntity.iuser.as("otherPersonIuser")
                        , chatRoomUserEntity2.userEntity.nm.as("otherPersonNm")
                        , chatRoomUserEntity2.userEntity.pic.as("otherPersonPic")
                ))
                .from(chatRoomEntity)
                .join(chatRoomEntity.chatRoomUserList, chatRoomUserEntity)
                //.on(chatRoomUserEntity.userEntity.iuser.eq(loginIuser))
                .join(chatRoomEntity.chatRoomUserList, chatRoomUserEntity2)
                //.on(chatRoomUserEntity2.userEntity.iuser.ne(loginIuser))
                .where(chatRoomUserEntity.userEntity.iuser.eq(loginIuser), chatRoomUserEntity2.userEntity.iuser.ne(loginIuser))
                .orderBy(chatRoomEntity.lastMsgAt.desc())
                .fetch();

    }

    @Override
    public List<ChatVo> selChatList(long ichatRoom, Pageable pageable) {
        return jpaQueryFactory.select(Projections.bean(ChatVo.class
                        , chatRoomMsgEntity.ichatMsg
                , chatRoomMsgEntity.msg
                , chatRoomMsgEntity.createdAt
                , userEntity.iuser
                , userEntity.nm.as("userNm")
                )).from(chatRoomMsgEntity)
                .join(chatRoomMsgEntity.userEntity, userEntity)
                .orderBy(chatRoomMsgEntity.ichatMsg.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
