package com.ssafy.i10a709be.domain.community.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomDetailDto {
    ChatRoomResponseDto chatRoomResponse;
    List<ChatMessageResponseDto> chatList;
    List<UserChatRoomResponseDto> userChatRoomList;
}
