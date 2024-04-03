package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.entity.Chat;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.product.entity.Product;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatRoomResponseDto {
    private long chatRoomId;
    private String memberId;
    private String nickname;
    private String title;
    private String imgSrc;
    private int capability;
    private ChatResponseDto chatResponseDto;

    public static ChatRoomResponseDto fromEntity(ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .memberId(chatRoom.getMember().getMemberId())
                .nickname(chatRoom.getMember().getNickname())
                .title(chatRoom.getTitle())
                .capability(chatRoom.getCapability())
                .build();
    }

    //다형성 활용
    public static ChatRoomResponseDto fromEntity( ChatRoom chatRoom, Chat chat){
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .memberId(chatRoom.getMember().getMemberId())
                .nickname(chatRoom.getMember().getNickname())
                .title(chatRoom.getTitle())
                .capability(chatRoom.getCapability())
                .chatResponseDto( ChatResponseDto.fromEntity( chat ))
                .build();
    }

    public static ChatRoomResponseDto fromEntity( ChatRoom chatRoom, Chat chat, Product product){
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .memberId(chatRoom.getMember().getMemberId())
                .nickname(chatRoom.getMember().getNickname())
                .title(chatRoom.getTitle())
                .imgSrc(product.getUnits().get(0).getUnitImages().get(0).getFiles().getSource())
                .capability(chatRoom.getCapability())
                .chatResponseDto( ChatResponseDto.fromEntity( chat ))
                .build();
    }
}
