package com.ssafy.i10a709be.domain.community.dto;

import com.ssafy.i10a709be.domain.community.enums.ChatRoomStatus;
import com.ssafy.i10a709be.domain.member.entity.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ChatRoomCreateDto {
    private List<Member> joinMembers;
    private String memberId;
    private String title;
    private Integer capability;
    private ChatRoomStatus status;
    private Long productId;
}