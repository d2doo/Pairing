package com.ssafy.i10a709be.common.exception;
import org.springframework.http.HttpStatus;


public interface ErrorCode {
    HttpStatus getHttpStatus();
    String getErrorName();
    String getErrorMessage();
}
