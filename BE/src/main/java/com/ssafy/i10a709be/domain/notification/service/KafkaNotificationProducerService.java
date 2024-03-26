package com.ssafy.i10a709be.domain.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i10a709be.domain.notification.dto.NotificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public void sendNotificationToKafkaTopic(String memberId, NotificationRequestDto notificationRequest) {
        String topicName = "notification-" + memberId;
        String payload = "";
        try {
            payload = objectMapper.writeValueAsString(notificationRequest);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            throw new RuntimeException("Object Mapping에 실패했습니다.");
        }
        kafkaTemplate.send(topicName, payload);
    }
}
