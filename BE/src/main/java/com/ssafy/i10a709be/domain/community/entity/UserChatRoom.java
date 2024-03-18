package com.ssafy.i10a709be.domain.community.entity;

import com.ssafy.i10a709be.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChatRoom {
    @Id @GeneratedValue
    private long userChatRoomId;

    @JoinColumn(name = "chat_room_id")
    @ManyToOne
    private ChatRoom chatRoom;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    LocalDateTime lastAccessTime;

    public void updateChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getUserChatRooms().add(this);
    }
}