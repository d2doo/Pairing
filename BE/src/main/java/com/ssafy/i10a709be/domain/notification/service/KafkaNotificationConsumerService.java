package com.ssafy.i10a709be.domain.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.notification.dto.NotificationCreateRequestDto;
import com.ssafy.i10a709be.domain.notification.dto.NotificationCreateResponseDto;
import com.ssafy.i10a709be.domain.notification.dto.NotificationResponseDto;
import com.ssafy.i10a709be.domain.notification.entity.Notification;
import com.ssafy.i10a709be.domain.notification.enums.NotificationType;
import com.ssafy.i10a709be.domain.notification.repository.NotificationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.i10a709be.domain.product.dto.UnitFindDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationConsumerService {
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ProductRepository productRepository;

    @Transactional
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
                        .notificationType(notificationRequest.getNotificationType())
                        .productId(notificationRequest.getProductId())
                        .build();
                notificationPool.add(notification);
            }
            for (Notification notification : notificationPool) {
                notificationRepository.save(notification);
                if( notification.getNotificationType().equals(NotificationType.confirm)){
                    Product p = productRepository.findById(notification.getProductId()).orElseThrow( () -> new EntityNotFoundException( notification.getProductId()+ "번 상품 엔티티를 찾을 수 없습니다."));
                    List<Unit> units = p.getUnits();
                    units.stream().forEach( u -> u.getOriginalProductId());
                    //N + 1
                    NotificationResponseDto dto = NotificationResponseDto.builder()
                            .notificationId(notification.getNotificationId())
                            .notificationType(notification.getNotificationType())
                            .content(notification.getContent())
                            .memberId(notification.getMember().getMemberId())
                            .isRead(notification.getIsRead())
                            .productId(notification.getProductId())
                            .units( units.stream().map(UnitFindDto::fromEntity).collect(Collectors.toList()))
                            .totalPrice(p.getTotalPrice())
                            .build();
                    simpMessagingTemplate.convertAndSend("/product-notification/" + dto.getMemberId(), objectMapper.writeValueAsString(dto));
                }else{
                    NotificationCreateResponseDto notificationResponse = NotificationCreateResponseDto.fromEntity(notification);
                    simpMessagingTemplate.convertAndSend("/product-notification/" + notificationResponse.getMemberId(), objectMapper.writeValueAsString(notificationResponse));
                }

            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException("Object Mapping에 실패했습니다.");
        }
    }
}
