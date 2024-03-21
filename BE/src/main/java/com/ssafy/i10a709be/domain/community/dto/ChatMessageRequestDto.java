package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.enums.ChatType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageRequestDto {
    private String memberId;
    private String content;
    private ChatType type;
    private long fileId;
}
