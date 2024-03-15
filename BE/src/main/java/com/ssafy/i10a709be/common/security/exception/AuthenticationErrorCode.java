package com.ssafy.i10a709be.common.security.exception;

import com.ssafy.i10a709be.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthenticationErrorCode implements ErrorCode {
    FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "잠시 후 다시 시도해주세요. 문제가 반복될 시에 wntjrdbs@gmail.com에 연락해주세요."
    );

    private final HttpStatus httpStatus;
    private final String errorMessage;

    @Override
    public String getErrorName() {
        return null;
    }
}
