package com.ssafy.i10a709be.domain.product.dto;

import com.ssafy.i10a709be.common.entity.Files;
import com.ssafy.i10a709be.domain.member.dto.MemberSummaryResponseDto;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitFindDto {
    private String memberId;
    private String nickname;
    private Integer score;
    private String unitDescription;
    private List<String> unitImages;
    private List<PartTypeDto> positions;
    private Integer age;

    public static UnitFindDto fromEntity(Unit unit){
        MemberSummaryResponseDto memberSummaryResponseDto = MemberSummaryResponseDto.fromEntity(unit.getMember());

        return UnitFindDto.builder()
                .memberId(memberSummaryResponseDto.getMemberId())
                .nickname(memberSummaryResponseDto.getNickname())
                .score(memberSummaryResponseDto.getScore())
                .unitDescription(unit.getUnitDescription())
                .unitImages(unit.getUnitImages().stream().map(files ->{
                    return files.getFiles().getSource();
                }).toList())
                .positions(unit.getParts().stream().map(part -> {
                    return PartTypeDto.fromEntity(part.getPartType());
                }).toList())
                .age(unit.getAge())
                .build();
    }
}
