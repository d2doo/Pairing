package com.ssafy.i10a709be.domain.community.controller;

import com.ssafy.i10a709be.domain.community.dto.ChatMessageDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.entity.Chat;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.community.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequestMapping("/chat")
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/room")
    public List<ChatRoom> getChatRoomLists(@RequestParam(name = "id") String id) {
        List<ChatRoom> chatRooms = chatService.findJoinedChatRooms(id);
        return chatRooms;
    }

    @PostMapping("/room")
    public Long createAndEnterChatRoom(ChatRoomCreateDto chatRoomCreateDto) {
        Optional<Long> chatRoomId = chatService.createChatRoom(chatRoomCreateDto).describeConstable();
        return chatRoomId.get();
    }

    @MessageMapping("/{roomId}")
    @SendTo("/chat-room/{roomId}")
    public Chat chat(@DestinationVariable Long roomId, ChatMessageDto message) {
        Chat chat = Chat.builder()
                .member()
                .build();
    }
}
