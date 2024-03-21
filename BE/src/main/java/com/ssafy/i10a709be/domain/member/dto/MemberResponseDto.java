package com.ssafy.i10a709be.domain.member.dto;

import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponseDto {
    private String memberId;
    private String email;
    private OAuthProvider provider;
    private String nickname;
    private String profileImage;
    private String address;
    private String phoneNumber;
    private Integer score;

    public static MemberResponseDto fromEntity(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .provider(member.getProvider())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .address(member.getAddress())
                .phoneNumber(member.getPhoneNumber())
                .score(member.getScore())
                .build();
    }
}