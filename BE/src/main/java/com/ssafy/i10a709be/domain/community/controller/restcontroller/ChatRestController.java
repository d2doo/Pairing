package com.ssafy.i10a709be.domain.community.controller.restcontroller;

import com.ssafy.i10a709be.domain.community.dto.*;
import com.ssafy.i10a709be.domain.community.entity.Chat;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.community.service.ChatService;
import com.ssafy.i10a709be.domain.product.service.ProductService;
import com.ssafy.i10a709be.domain.community.service.KafkaChatProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatRestController {
    private final ChatService chatService;
    private final ProductService productService;
    private final KafkaChatProducerService kafkaChatProducerService;

    /*TODO
       - Kafka 연결
       - 알림 기능
         - 구매 신청, 합의 생성, 거래 완료 시 알ㄹ미
       - 관련 상품 정보를 보내주어야 채팅창에 해당 정보가 보인다.
       - + 이미지 업로드 기능

     */
    @GetMapping("/room")
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRoomList(@RequestParam String memberId) {
        List<ChatRoomResponseDto> chatRoomResponseList = chatService.findJoinedChatRooms(memberId).stream()
                .map( croom -> {
                    Chat c = chatService.findLatestChatByChatRoomId( croom.getChatRoomId() );
                    return ChatRoomResponseDto.fromEntity( croom, c );
                }).toList();
        return ResponseEntity.ok(chatRoomResponseList);
    }

    @PostMapping("/room")
    public ResponseEntity<Long> createAndEnterChatRoom(@RequestBody ChatRoomCreateDto chatRoomCreateDto) {
        Long chatRoomId = chatService.createChatRoom(chatRoomCreateDto);
        return ResponseEntity.ok(chatRoomId);
    }

    @GetMapping( "/room/detail/{roomId}")
    public ResponseEntity<ChatRoomDetailDto> getRoomDetail(@PathVariable Long roomId ){
        ChatRoom findRoom = chatService.findChatRoomById(roomId);
        ChatRoomResponseDto chatRoom = ChatRoomResponseDto.fromEntity(findRoom);
        List<ChatMessageResponseDto> chatList = chatService.findChatsByChatRoomId(roomId).stream()
                .map(ChatMessageResponseDto::fromEntity).toList();
        List<UserChatRoomResponseDto> userChatRoomList = chatService.findUserChatRoomsByChatRoomId(roomId).stream()
                .map(UserChatRoomResponseDto::fromEntity).toList();
        ChatRoomProductDto chatRoomProductDto = ChatRoomProductDto.fromEntity( productService.findProduct(findRoom.getProductId()));
        return ResponseEntity.ok(ChatRoomDetailDto.builder()
                .chatRoomResponse(chatRoom)
                .chatList(chatList)
                .userChatRoomList(userChatRoomList)
                .chatRoomProduct(chatRoomProductDto)
                .build());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomResponseDto> getChatRoomInfo(@PathVariable Long roomId) {
        ChatRoomResponseDto chatRoomResponseDto = ChatRoomResponseDto.fromEntity(chatService.findChatRoomById(roomId));
        return ResponseEntity.ok(chatRoomResponseDto);
    }

    @MessageMapping("/chat/{roomId}")
    public ResponseEntity<ChatMessageResponseDto> chat(@DestinationVariable Long roomId, ChatMessageRequestDto chatMessageRequestDto) {
        kafkaChatProducerService.sendMessageToKafkaTopic( roomId, chatMessageRequestDto);
        log.info("incoming:"+" " + chatMessageRequestDto.getContent() );
        return ResponseEntity.ok().build();
    }
}
