package com.ssafy.i10a709be.domain.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.notification.dto.NotificationCreateRequestDto;
import com.ssafy.i10a709be.domain.notification.dto.NotificationCreateResponseDto;
import com.ssafy.i10a709be.domain.notification.entity.Notification;
import com.ssafy.i10a709be.domain.notification.repository.NotificationRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationConsumerService {
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "product-notification", groupId = "productGroup")
    public void consume(ConsumerRecord<String, String> message) {
        try {
            log.info("Consume Messasge : " + message.value());
            NotificationCreateRequestDto notificationRequest = objectMapper.readValue(message.value(), NotificationCreateRequestDto.class);
            ArrayList<Notification> notificationPool = new ArrayList<>();
            for (String memberId : notificationRequest.getMembers()) {
                Notification notification = Notification.builder()
                        .member(memberRepository.findById(memberId)
                                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
                        .content(notificationRequest.getContent())
                        .isRead(notificationRequest.getIsRead())
                        .build();
                notificationPool.add(notification);
            }
            for (Notification notification : notificationPool) {
                notificationRepository.save(notification);
                NotificationCreateResponseDto notificationResponse = NotificationCreateResponseDto.fromEntity(notification);
                simpMessagingTemplate.convertAndSend("/product-notification/" + notificationResponse.getMemberId(), objectMapper.writeValueAsString(notificationResponse));
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException("Object Mapping에 실패했습니다.");
        }
    }
}
