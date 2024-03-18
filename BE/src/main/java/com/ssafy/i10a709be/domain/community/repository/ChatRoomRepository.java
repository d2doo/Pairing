package com.ssafy.i10a709be.domain.community.repository;

import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select crooms from ChatRoom crooms where crooms.member.memberId =: memberId")
    List<ChatRoom> findByMemberId(Long memberId);

    @Query("select crooms from ChatRoom crooms join fetch crooms.chats where crooms.chatRoomId =: chatRoomId")
    Optional<ChatRoom> findChatRoomByChatRoomIdAndChats( Long chatRoomId );


}
