package com.ssafy.i10a709be.domain.product.dto;

import com.ssafy.i10a709be.domain.member.dto.MemberDetailResponseDto;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitFindDto {
    private String memberId;
    private String nickname;
    List<PartTypeDto> positions;
    private int age;

    public static UnitFindDto fromEntity(Unit unit){
        MemberDetailResponseDto memberDetailDto = MemberDetailResponseDto.fromEntity(unit.getMember());

        return UnitFindDto.builder()
                .memberId(memberDetailDto.getMemberId())
                .nickname(memberDetailDto.getNickname())
                .positions(unit.getParts().stream().map(part -> {
                    return PartTypeDto.fromEntity(part.getPartType());
                }).toList())
                .age(unit.getAge())
                .build();
    }
}
