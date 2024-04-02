package com.ssafy.i10a709be.domain.notification.controller;

import com.ssafy.i10a709be.domain.notification.dto.NotificationCreateRequestDto;
import com.ssafy.i10a709be.domain.notification.dto.NotificationResponseDto;
import com.ssafy.i10a709be.domain.notification.entity.Notification;
import com.ssafy.i10a709be.domain.notification.enums.NotificationType;
import com.ssafy.i10a709be.domain.notification.service.KafkaNotificationProducerService;
import com.ssafy.i10a709be.domain.notification.service.NotificationService;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.i10a709be.domain.product.dto.UnitFindDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationRestController {
    private final KafkaNotificationProducerService kafkaNotificationProducerService;
    private final NotificationService notificationService;
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getNotifications(@AuthenticationPrincipal String memberId) {
        List<Notification> notifications = notificationService.getNotifications(memberId);
        List<NotificationResponseDto> dtoList = notifications.stream().map( e -> {
            NotificationResponseDto dto = NotificationResponseDto.builder()
                    .notificationId(e.getNotificationId())
                    .isRead(e.getIsRead())
                    .content(e.getContent())
                    .memberId(memberId)
                    .notificationType(e.getNotificationType())
//                    .productId(e.getProductId()
                    .build();
            if( e.getNotificationType().equals( NotificationType.message ) ) return dto;
            //N+1 안 급함 나중에
            Product p = productService.findProduct( e.getProductId() );
            dto.setProductId(e.getProductId());
            dto.setTotalPrice(p.getTotalPrice());
            List< UnitFindDto> unitFindDtoList = p.getUnits().stream().filter( u -> u.getMember().getMemberId() != memberId ).map(UnitFindDto::fromEntity).collect(Collectors.toList());
            dto.setUnits( unitFindDtoList );
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtoList);
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
