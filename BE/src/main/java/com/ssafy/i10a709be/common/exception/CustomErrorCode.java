package com.ssafy.i10a709be.common.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{

    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "잠시 후 다시 시도해주세요. 문제가 반복될 시에 wntjrdbs@gmail.com에 연락해주세요."
            ), FILE_IS_NOT_IMAGE_TYPE(HttpStatus.INTERNAL_SERVER_ERROR,"이미지 파일 형식이 지원하지 않습니다.") ;

    private final HttpStatus httpStatus;
    private final String errorMessage;
    @Override
    public String getErrorName() {
        return this.name();
    }




}
