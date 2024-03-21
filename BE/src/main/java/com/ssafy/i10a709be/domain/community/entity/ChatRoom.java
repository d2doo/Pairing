package com.ssafy.i10a709be.domain.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.i10a709be.common.entity.BaseEntity;
import com.ssafy.i10a709be.domain.community.enums.ChatRoomStatus;
import com.ssafy.i10a709be.domain.member.entity.Member;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@SQLRestriction("status = 'active'")
public class ChatRoom extends BaseEntity {
    @Id @GeneratedValue
    private long chatRoomId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private int capability;

    @Enumerated(EnumType.STRING)
    private ChatRoomStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom" , cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Chat> chats;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<UserChatRoom> userChatRooms;

    @Override
    public String toString() {
        return "ChatRoom{" +
                "chatRoomId=" + chatRoomId +
                ", member=" + member +
                ", title='" + title + '\'' +
                ", capability=" + capability +
                ", status=" + status +
                ", chats=" + chats +
                ", userChatRooms=" + userChatRooms +
                '}';
    }
}