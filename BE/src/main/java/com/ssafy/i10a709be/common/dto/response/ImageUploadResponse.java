package com.ssafy.i10a709be.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageUploadResponse {
    private String imgUrl;
    public static ImageUploadResponse from(String s) {
        return ImageUploadResponse.builder().imgUrl( s ).build();
    }
}
