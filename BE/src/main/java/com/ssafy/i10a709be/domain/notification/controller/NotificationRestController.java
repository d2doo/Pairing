package com.ssafy.i10a709be.domain.notification.controller;

import com.ssafy.i10a709be.domain.notification.dto.NotificationRequestDto;
import com.ssafy.i10a709be.domain.notification.service.KafkaNotificationProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationRestController {
    private final KafkaNotificationProducerService kafkaNotificationProducerService;

    @MessageMapping("/notification")
    public ResponseEntity<Void> sendNotificationToMember(NotificationRequestDto notificationRequest) {
        kafkaNotificationProducerService.sendNotificationToKafkaTopic(notificationRequest);
        return ResponseEntity.ok().build();
    }
}
