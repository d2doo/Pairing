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
    //원래는 ChatRoomResponseDto를 바꿔야하지만 변동사항이 많아 추가하는 방식으로 가겠음.
    ChatRoomProductDto chatRoomProduct;

}
