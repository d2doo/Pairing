package com.ssafy.i10a709be.common.service;

import com.ssafy.i10a709be.common.entity.Files;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    /**
     * MultipartFile을 업로드하고, 주소를 리턴합니다. 만약 MultipartFile이 존재하지 않는다면 null을 리턴합니다.
     *
     * @param multipartFile
     * @return source of image or null
     */
    Files uploadFile(MultipartFile multipartFile);

}

