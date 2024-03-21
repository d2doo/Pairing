package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.entity.UserChatRoom;
import com.ssafy.i10a709be.domain.member.dto.MemberDetailResponseDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserChatRoomResponseDto {
    private long userChatRoomId;
    private MemberDetailResponseDto member;
    private LocalDateTime lastAccessTime;

    public static UserChatRoomResponseDto fromEntity(UserChatRoom userChatRoom) {
        return UserChatRoomResponseDto.builder()
                .userChatRoomId(userChatRoom.getUserChatRoomId())
                .member(MemberDetailResponseDto.fromEntity(userChatRoom.getMember()))
                .lastAccessTime(userChatRoom.getLastAccessTime())
                .build();
    }
}
