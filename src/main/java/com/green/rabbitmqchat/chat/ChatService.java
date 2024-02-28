package com.green.rabbitmqchat.chat;

import com.green.rabbitmqchat.chat.model.ChatDto;
import com.green.rabbitmqchat.chat.model.ChatRoomDto;
import com.green.rabbitmqchat.chat.model.ChatRoomVo;
import com.green.rabbitmqchat.chat.model.ChatVo;
import com.green.rabbitmqchat.common.ResVo;
import com.green.rabbitmqchat.entity.ChatRoomEntity;
import com.green.rabbitmqchat.entity.ChatRoomMsgEntity;
import com.green.rabbitmqchat.entity.ChatRoomUserEntity;
import com.green.rabbitmqchat.entity.UserEntity;
import com.green.rabbitmqchat.exception.ChatErrorCode;
import com.green.rabbitmqchat.exception.RestApiException;
import com.green.rabbitmqchat.security.AuthenticationFacade;
import com.green.rabbitmqchat.security.MyPrincipal;
import com.green.rabbitmqchat.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final AuthenticationFacade authenticationFacade;
    private final ChatRoomRepository repository;

    public List<ChatVo> getChatList(long ichatRoom, Pageable pageable) {
        return repository.selChatList(ichatRoom, pageable);
    }

    public List<ChatRoomVo> getRoomList() {
        return repository.selRoomList(authenticationFacade.getLoginUserPk());
    }

    @Transactional
    public ResVo postRoom(ChatRoomDto dto) {
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        repository.save(chatRoomEntity);

        List<ChatRoomUserEntity> chatRoomUserEntityList = new ArrayList();

        ChatRoomUserEntity meChatRoomUserEntity = new ChatRoomUserEntity();
        meChatRoomUserEntity.setChatRoomEntity(chatRoomEntity);
        meChatRoomUserEntity.setUserEntity(UserEntity.builder()
                .iuser(authenticationFacade.getLoginUserPk())
                .build());

        ChatRoomUserEntity otherPersonChatRoomUserEntity = new ChatRoomUserEntity();
        otherPersonChatRoomUserEntity.setChatRoomEntity(chatRoomEntity);
        otherPersonChatRoomUserEntity.setUserEntity(UserEntity.builder()
                .iuser(dto.getOtherPersonIuser())
                .build());

        chatRoomUserEntityList.add(meChatRoomUserEntity);
        chatRoomUserEntityList.add(otherPersonChatRoomUserEntity);

        chatRoomEntity.setChatRoomUserList(chatRoomUserEntityList);
        return new ResVo(1);
    }

    @Transactional
    public void send(ChatDto dto) {
        ChatRoomEntity chatRoomEntity = repository.getReferenceById(dto.getIchatRoom());
        if(chatRoomEntity == null) {
            throw new RestApiException(ChatErrorCode.ERR_OCCURRED);
        }

        ChatRoomMsgEntity chatRoomMsgEntity = ChatRoomMsgEntity.builder()
                .chatRoomEntity(chatRoomEntity)
                .userEntity(UserEntity.builder().iuser(dto.getSendIuser()).build())
                .msg(dto.getMessage())
                .build();

        chatRoomEntity.setLastMsg(dto.getMessage());
        chatRoomEntity.getChatRoomMsgList().add(chatRoomMsgEntity);
    }
}
