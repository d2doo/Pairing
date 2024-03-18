package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.enums.ChatType;
import lombok.Getter;

@Getter
public class ChatMessageDto {
    private String memberId;
    private Long chatRoomId;
    private String content;
    private ChatType type;
    private long fileId;
}
