package com.ssafy.i10a709be.domain.notification.dto;

import com.ssafy.i10a709be.domain.notification.enums.NotificationType;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotificationCreateRequestDto {
    private String topicSubject;
    private ArrayList<String> members;
    private String content;
    private Boolean isRead;
    private NotificationType notificationType;
    private Long productId;
}
