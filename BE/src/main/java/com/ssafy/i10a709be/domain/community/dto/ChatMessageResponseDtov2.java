package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.entity.Chat;
import com.ssafy.i10a709be.domain.community.enums.ChatType;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDtov2 {
    private Long chatRoomId;
    private Long chatId;
    private String memberId;
    private String nickname;
    private String profileImage;
    private String content;
    private ChatType type;

    public static ChatMessageResponseDtov2 fromEntity(Chat chat) {
        return ChatMessageResponseDtov2.builder()
                .chatRoomId(chat.getChatRoom().getChatRoomId())
                .chatId(chat.getChatId())
                .memberId(chat.getMember().getMemberId())
                .nickname(chat.getMember().getNickname())
                .content(chat.getContent())
                .type(chat.getType())
                .profileImage( chat.getMember().getProfileImage())
                .build();
    }
}
