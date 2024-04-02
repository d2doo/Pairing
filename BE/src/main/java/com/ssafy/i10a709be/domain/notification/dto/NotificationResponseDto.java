package com.ssafy.i10a709be.domain.notification.dto;

import com.ssafy.i10a709be.domain.notification.enums.NotificationType;
import com.ssafy.i10a709be.domain.product.dto.UnitFindDto;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class NotificationResponseDto {
    private Long notificationId;
    private String memberId;
    private String content;
    private Boolean isRead;
    private NotificationType notificationType;
    private Long productId;
    private List<UnitFindDto> units;
    private Integer totalPrice;
}

