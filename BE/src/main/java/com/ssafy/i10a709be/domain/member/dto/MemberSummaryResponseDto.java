package com.ssafy.i10a709be.domain.member.dto;

import com.ssafy.i10a709be.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@Data
public class MemberSummaryResponseDto {
    private String memberId;
    private String nickname;
    private String profileImage;
    private Integer score;

    public static MemberSummaryResponseDto fromEntity(Member member) {
        return MemberSummaryResponseDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .score(member.getScore())
                .build();
    }

    public static MemberSummaryResponseDto fromEntityWithMemberId(Member member,String memberId) {
        return MemberSummaryResponseDto.builder()
                .memberId(memberId)
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .score(member.getScore())
                .build();
    }
}
