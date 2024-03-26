package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDto {
    public String content;
    public LocalDateTime createdAt;

    public static ChatResponseDto fromEntity(Chat chat){
        return ChatResponseDto.builder()
                .content( chat.getContent() )
                .createdAt( chat.getCreatedAt() == null ? LocalDateTime.now() : chat.getCreatedAt())
                .build();
    }
}
