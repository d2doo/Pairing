package com.ssafy.i10a709be.domain.community.service;

import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomDeleteDto;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;

public interface ChatService {
    List<ChatRoom> findJoinedChatRooms(Long memberId);
    Long createChatRoom(ChatRoomCreateDto chatRoomCreateDto);
    void deleteChatRoom(ChatRoomDeleteDto chatRoomDeleteDto);

    Optional<ChatRoom> findChatRoomByChatRoomIdAndChats( Long chatRoomId );
}
