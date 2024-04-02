package com.ssafy.i10a709be.domain.notification.dto;


import com.ssafy.i10a709be.domain.notification.entity.Notification;
import com.ssafy.i10a709be.domain.notification.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreateResponseDto {
    private Long notificationId;
    private String memberId;
    private String content;
    private Boolean isRead;
    private NotificationType notificationType;
    private Long productId;

    public static NotificationCreateResponseDto fromEntity(Notification notification) {
        return NotificationCreateResponseDto.builder()
                .notificationId(notification.getNotificationId())
                .memberId(notification.getMember().getMemberId())
                .content(notification.getContent())
                .isRead(notification.getIsRead())
                .notificationType(notification.getNotificationType())
                .productId(notification.getProductId())
                .build();
    }
}
