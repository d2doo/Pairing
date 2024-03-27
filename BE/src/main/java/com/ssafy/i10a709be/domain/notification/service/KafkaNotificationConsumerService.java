package com.ssafy.i10a709be.domain.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.notification.dto.NotificationRequestDto;
import com.ssafy.i10a709be.domain.notification.entity.Notification;
import com.ssafy.i10a709be.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationConsumerService {
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    public void consume(String message) {
        try {
            NotificationRequestDto notificationRequest = objectMapper.readValue(message, NotificationRequestDto.class);
            Notification notification = Notification.builder()
                    .member(memberRepository.findById(notificationRequest.getMemberId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
                    .content(notificationRequest.getContent())
                    .build();
            notificationRepository.save(notification);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException("Object Mapping에 실패했습니다.");
        }
    }
}
