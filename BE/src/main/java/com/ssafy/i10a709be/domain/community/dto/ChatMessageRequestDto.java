package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.enums.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequestDto implements Serializable {
    private String memberId;
    private Long roomId;
    private String content;
    private ChatType type;
    private Long fileId;
}
