package com.ssafy.i10a709be.domain.notification.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotificationRequestDto {
    private String topicSubject;
    private ArrayList<String> members;
    private String content;
}
