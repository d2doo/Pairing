package com.ssafy.i10a709be.common.controller.RestController;

import com.ssafy.i10a709be.common.dto.request.ImageRequestDto;
import com.ssafy.i10a709be.common.dto.response.ImageUploadResponse;
import com.ssafy.i10a709be.common.entity.Files;
import com.ssafy.i10a709be.common.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/common")
@RestController

public class FileUploadRestController {
    private final FileUploadService fileUploadService;

    @PostMapping("/image")
    public ResponseEntity<ImageUploadResponse> uploadImageFile(
            @RequestPart("image") MultipartFile multipartFile) {
        return ResponseEntity.ok(ImageUploadResponse.from(fileUploadService.uploadFile(multipartFile)));
    }

    @PostMapping("/images") // get이 맞긴한데 body
    public ResponseEntity<List<ImageUploadResponse>> findImages( @RequestBody ImageRequestDto imageIds ){
//        System.out.println(imageIds.toString());
        List<Files> result = fileUploadService.findAllByFileId( imageIds.getImageIds() );
//        System.out.println(Arrays.toString((result.stream().toArray())));
        return ResponseEntity.ok( result.stream().map( ImageUploadResponse::from ).collect(Collectors.toList()));
    }

}
