package com.ssafy.i10a709be.domain.community.repository;

import com.ssafy.i10a709be.domain.community.entity.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    @Query("select ucrooms from UserChatRoom ucrooms where ucrooms.chatRoom.chatRoomId = :chatRoomId")
    public List<UserChatRoom> findUserChatRoomsByChatRoomId(@Param(value = "chatRoomId") Long chatRoomId);
}
