package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.entity.Chat;
import com.ssafy.i10a709be.domain.community.enums.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {
    private Long chatRoomId;
    private Long chatId;
    private String memberId;
    private String nickname;
    private String content;
    private ChatType type;

    public static ChatMessageResponseDto fromEntity(Chat chat) {
        return ChatMessageResponseDto.builder()
                .chatRoomId(chat.getChatRoom().getChatRoomId())
                .chatId(chat.getChatId())
                .memberId(chat.getMember().getMemberId())
                .nickname(chat.getMember().getNickname())
                .content(chat.getContent())
                .type(chat.getType())
                .build();
    }
}
