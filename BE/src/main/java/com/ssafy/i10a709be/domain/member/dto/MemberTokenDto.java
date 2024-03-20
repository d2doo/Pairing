package com.ssafy.i10a709be.domain.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class MemberTokenDto {
    private String accessToken;
    private String refreshToken;
}
