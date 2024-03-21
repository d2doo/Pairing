package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomResponseDto {
    private long chatRoomId;
    private String memberId;
    private String nickname;
    private String title;
    private int capability;

    public static ChatRoomResponseDto fromEntity(ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .memberId(chatRoom.getMember().getMemberId())
                .nickname(chatRoom.getMember().getNickname())
                .title(chatRoom.getTitle())
                .capability(chatRoom.getCapability())
                .build();
    }
}
