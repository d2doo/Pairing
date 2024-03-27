package com.ssafy.i10a709be.domain.member.dto;

import com.ssafy.i10a709be.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberUpdateRequestDto {
    private String nickname;
    private String profileImage;
    private String address;
    private String phoneNumber;

    public static MemberUpdateRequestDto fromEntity(Member member) {
        return MemberUpdateRequestDto.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .address(member.getAddress())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}