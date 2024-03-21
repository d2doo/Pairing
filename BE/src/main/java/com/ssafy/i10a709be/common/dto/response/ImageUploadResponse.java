package com.ssafy.i10a709be.common.dto.response;

import com.ssafy.i10a709be.common.entity.Files;
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
    private Long fileId;
    public static ImageUploadResponse from(Files files) {
        return ImageUploadResponse.builder().imgUrl(files.getSource() ).fileId(files.getFileId()).build();
    }
}
