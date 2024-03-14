package com.ssafy.i10a709be.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginResDto {
    private String email;
    private String nickname;
    private String profileImage;
}