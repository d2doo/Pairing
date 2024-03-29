package com.ssafy.i10a709be.domain.notification.dto;


import com.ssafy.i10a709be.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {
    private String memberId;
    private String content;

    public static NotificationResponseDto fromEntity(Notification notification) {
        return NotificationResponseDto.builder()
                .memberId(notification.getMember().getMemberId())
                .content(notification.getContent())
                .build();
    }
}
