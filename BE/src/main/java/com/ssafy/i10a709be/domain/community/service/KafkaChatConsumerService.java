package com.ssafy.i10a709be.domain.community.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i10a709be.domain.community.dto.ChatMessageRequestDto;
import com.ssafy.i10a709be.domain.community.dto.ChatMessageResponseDto;
import com.ssafy.i10a709be.domain.community.entity.Chat;
import com.ssafy.i10a709be.domain.community.repository.ChatRepository;
import com.ssafy.i10a709be.domain.community.repository.ChatRoomRepository;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaChatConsumerService {
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    public void consume(ConsumerRecord<String,String> message) {
        try {
            System.out.println(message.value());
            ChatMessageRequestDto chatMessage = objectMapper.readValue(message.value(), ChatMessageRequestDto.class);

            Chat chat = Chat.builder()
                    .member(memberRepository.findById(chatMessage.getMemberId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
                    .chatRoom(chatRoomRepository.findById(chatMessage.getRoomId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 채팅방을 찾을 수 없습니다.")))
                    .content(chatMessage.getContent())
                    .type(chatMessage.getType())
                    .fileId(chatMessage.getFileId())
                    .build();
            chatRepository.save(chat);
            simpMessagingTemplate.convertAndSend("/chat-room/"+chatMessage.getRoomId(), objectMapper.writeValueAsString( ChatMessageResponseDto.fromEntity(chat)));

        } catch (Exception e){
            log.info(e.getMessage());
            throw new RuntimeException("Object Mapping에 실패했습니다.");
        }
    }

}
