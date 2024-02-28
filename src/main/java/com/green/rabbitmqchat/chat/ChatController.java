package com.green.rabbitmqchat.chat;

import com.green.rabbitmqchat.chat.model.ChatRoomDto;
import com.green.rabbitmqchat.chat.model.ChatRoomVo;
import com.green.rabbitmqchat.chat.model.ChatVo;
import com.green.rabbitmqchat.common.ResVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService service;

    @GetMapping
    public List<ChatVo> getChatList(@RequestParam("ichat_room") long ichatRoom, Pageable pageable) {
        return service.getChatList(ichatRoom, pageable);
    }

    @GetMapping("room")
    public List<ChatRoomVo> getRoomList() {
        return service.getRoomList();
    }

    @PostMapping("room")
    public ResVo postRoom(@RequestBody ChatRoomDto dto) {
        return service.postRoom(dto);
    }

}
