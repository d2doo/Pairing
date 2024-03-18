package com.ssafy.i10a709be.domain.community.repository;

import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select crooms from ChatRoom crooms where crooms.member.memberId =: memberId")
    List<ChatRoom> findByMemberId(Long memberId);
}
