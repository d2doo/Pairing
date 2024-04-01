package com.ssafy.i10a709be.domain.notification.controller;

import com.ssafy.i10a709be.domain.notification.dto.NotificationCreateRequestDto;
import com.ssafy.i10a709be.domain.notification.dto.NotificationCreateResponseDto;
import com.ssafy.i10a709be.domain.notification.entity.Notification;
import com.ssafy.i10a709be.domain.notification.service.KafkaNotificationProducerService;
import com.ssafy.i10a709be.domain.notification.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationRestController {
    private final KafkaNotificationProducerService kafkaNotificationProducerService;
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationCreateResponseDto>> getNotifications(@AuthenticationPrincipal String memberId) {
        List<Notification> notifications = notificationService.getNotifications(memberId);
        return ResponseEntity.ok().body(notifications.stream().map(NotificationCreateResponseDto::fromEntity).toList());
    }

    @PutMapping
    public ResponseEntity<Void> readNotifications(@AuthenticationPrincipal String memberId, @RequestBody List<Long> targetNotifications) {
        notificationService.readNotifications(memberId, targetNotifications);
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/notification")
    public ResponseEntity<Void> sendNotificationToMember(NotificationCreateRequestDto notificationRequest) {
        kafkaNotificationProducerService.sendNotificationToKafkaTopic(notificationRequest);
        return ResponseEntity.ok().build();
    }
}
