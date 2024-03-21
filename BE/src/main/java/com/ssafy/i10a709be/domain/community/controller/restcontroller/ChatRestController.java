package com.ssafy.i10a709be.domain.community.controller.restcontroller;

import com.ssafy.i10a709be.domain.community.dto.ChatMessageRequestDto;
import com.ssafy.i10a709be.domain.community.dto.ChatMessageResponseDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomDetailDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomResponseDto;
import com.ssafy.i10a709be.domain.community.dto.UserChatRoomResponseDto;
import com.ssafy.i10a709be.domain.community.entity.Chat;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.community.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatRestController {
    private final ChatService chatService;

    @GetMapping("/room")
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRoomList(@RequestParam String memberId) {
        List<ChatRoomResponseDto> chatRoomResponseList = chatService.findJoinedChatRooms(memberId).stream()
                .map(ChatRoomResponseDto::fromEntity).toList();
        return ResponseEntity.ok(chatRoomResponseList);
    }

    @PostMapping("/room")
    public ResponseEntity<Long> createAndEnterChatRoom(@RequestBody ChatRoomCreateDto chatRoomCreateDto) {
        Long chatRoomId = chatService.createChatRoom(chatRoomCreateDto);
        return ResponseEntity.ok(chatRoomId);
    }

    @GetMapping( "/room/detail/{roomId}")
    public ResponseEntity<ChatRoomDetailDto> getRoomDetail(@PathVariable Long roomId ){
        ChatRoomResponseDto chatRoom = ChatRoomResponseDto.fromEntity(chatService.findChatRoomById(roomId));
        List<ChatMessageResponseDto> chatList = chatService.findChatsByChatRoomId(roomId).stream()
                .map(ChatMessageResponseDto::fromEntity).toList();
        List<UserChatRoomResponseDto> userChatRoomList = chatService.findUserChatRoomsByChatRoomId(roomId).stream()
                .map(UserChatRoomResponseDto::fromEntity).toList();
        return ResponseEntity.ok(ChatRoomDetailDto.builder()
                .chatRoomResponse(chatRoom)
                .chatList(chatList)
                .userChatRoomList(userChatRoomList)
                .build());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomResponseDto> getChatRoomInfo(@PathVariable Long roomId) {
        ChatRoomResponseDto chatRoomResponseDto = ChatRoomResponseDto.fromEntity(chatService.findChatRoomById(roomId));
        return ResponseEntity.ok(chatRoomResponseDto);
    }

    @MessageMapping("/{roomId}")
    @SendTo("/chat-room/{roomId}")
    public ResponseEntity<ChatMessageResponseDto> chat(@DestinationVariable Long roomId, ChatMessageRequestDto chatMessageRequestDto) {
        Chat chat = chatService.saveChatMessage(chatMessageRequestDto, roomId);
        return ResponseEntity.ok(ChatMessageResponseDto.fromEntity( chat ));
    }
}
