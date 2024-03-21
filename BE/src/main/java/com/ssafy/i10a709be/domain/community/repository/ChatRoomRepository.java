package com.ssafy.i10a709be.domain.community.repository;

import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import java.util.List;
import java.util.Optional;

import com.ssafy.i10a709be.domain.community.enums.ChatRoomStatus;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select crooms from ChatRoom crooms where exists ( select ucrooms.chatRoom from UserChatRoom ucrooms where ucrooms.member.memberId = :memberId)")
    List<ChatRoom> findChatRoomsByJoinedMemberId( @Param( value = "memberId") String memberId);
}
