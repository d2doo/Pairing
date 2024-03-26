package com.ssafy.i10a709be.domain.community.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i10a709be.domain.community.dto.ChatMessageRequestDto;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaChatProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessageToKafkaTopic(Long roomId, ChatMessageRequestDto chatMessageRequest) {
        String topicName = "chat-room-" + roomId;
        String payload = "";
        try {
            payload = objectMapper.writeValueAsString(chatMessageRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Object Mapping에 실패했습니다.");
        }
        log.info(topicName + " " + payload);

        kafkaTemplate.send(topicName, payload);
    }
}
