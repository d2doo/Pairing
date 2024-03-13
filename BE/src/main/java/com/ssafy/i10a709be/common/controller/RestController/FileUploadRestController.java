package com.ssafy.i10a709be.common.controller.RestController;

import com.ssafy.i10a709be.common.dto.response.ImageUploadResponse;
import com.ssafy.i10a709be.common.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/common")
@RestController

public class FileUploadRestController {
    private final FileUploadService fileUploadService;

    @PostMapping("/image")
    public ResponseEntity<ImageUploadResponse> uploadImageFile(
            @RequestPart("image") MultipartFile multipartFile) {
        return ResponseEntity.ok(ImageUploadResponse.from(fileUploadService.uploadFile(multipartFile)));
    }

}
