package com.ssafy.i10a709be.domain.community.entity;

import com.ssafy.i10a709be.common.entity.BaseEntity;
import com.ssafy.i10a709be.domain.member.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChatRoom extends BaseEntity {
    @Id @GeneratedValue
    private Long userChatRoomId;

    @JoinColumn(name = "chat_room_id")
    @ManyToOne
    private ChatRoom chatRoom;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    LocalDateTime lastAccessTime;

    @Override
    public String toString() {
        return "UserChatRoom{" +
                "userChatRoomId=" + userChatRoomId +
                ", chatRoom=" + chatRoom.getTitle() +
                ", member=" + member +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }
}