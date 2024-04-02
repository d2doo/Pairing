package com.ssafy.i10a709be.domain.community.service;

import com.ssafy.i10a709be.domain.community.dto.ChatMessageRequestDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomDeleteDto;
import com.ssafy.i10a709be.domain.community.entity.Chat;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.community.entity.UserChatRoom;
import java.util.List;

public interface ChatService {
    List<ChatRoom> findJoinedChatRooms(String memberId);
    ChatRoom findChatRoomById(Long chatRoomId);
    Long createChatRoom(ChatRoomCreateDto chatRoomCreateDto);
    void deleteChatRoom(ChatRoomDeleteDto chatRoomDeleteDto);

    Chat saveChatMessage(ChatMessageRequestDto chatMessageRequestDto, Long roomId);

    List< Chat > findChatsByChatRoomId(Long roomId );

    List<UserChatRoom> findUserChatRoomsByChatRoomId(Long roomId);

    Chat findLatestChatByChatRoomId(Long roomId);
}
