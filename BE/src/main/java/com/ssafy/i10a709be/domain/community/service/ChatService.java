package com.ssafy.i10a709be.domain.community.service;

import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomDeleteDto;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.member.entity.Member;
import java.util.List;

public interface ChatService {
    List<ChatRoom> findJoinedChatRooms(Long memberId);
    Long createChatRoom(ChatRoomCreateDto chatRoomCreateDto);
    void deleteChatRoom(ChatRoomDeleteDto chatRoomDeleteDto);
}
