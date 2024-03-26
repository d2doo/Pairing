package com.ssafy.i10a709be.domain.community.repository;

import com.ssafy.i10a709be.domain.community.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findChatsByChatRoom_ChatRoomId( @Param( value = "roomId" ) Long roomId);

    @Query("select c from Chat c where c.chatRoom.chatRoomId =:roomId order by c.chatId desc limit 1")
    Optional<Chat> findLatestChatByChatRoomId( @Param( value = "roomId") Long roomId );
}
