package com.ssafy.i10a709be.domain.member.dto;

import com.ssafy.i10a709be.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDetailResponseDto {
    private String memberId;
    private String nickname;
    private String profileImage;

    public static MemberDetailResponseDto fromEntity(Member member) {
        return MemberDetailResponseDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();
    }
}
