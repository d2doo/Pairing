package com.ssafy.i10a709be.common.exception;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            CustomException e, HttpServletRequest request) {
        log.debug("Custom Error 발생", e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.createErrorResponse(e.getErrorCode(), request.getRequestURI()));
    }

    @ExceptionHandler(MultiPartFileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMultiPartFileNotFoundException(
            Exception e, HttpServletRequest request) {
        log.warn("MultiPartFileNotFound Exception 발생!!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.createErrorResponse(
                                CustomErrorCode.FILE_NOT_PROVIDED, request.getRequestURI()
                        )
                );
    }
    @ExceptionHandler( OriginalProductNotFoundException.class )
    public ResponseEntity<ErrorResponse> handleOriginalProductNotFoundException(
            OriginalProductNotFoundException e, HttpServletRequest request){
        log.info("유닛 product 복구간 에러 발생!! 발생 위치: {}\n", e.getFrom(), e.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.createErrorResponse(
                                CustomErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURI()));
    }
    @ExceptionHandler( CustomJpaPersistenceException.class )
    public ResponseEntity<ErrorResponse> handleCustomJpaPersistenceException(
            CustomJpaPersistenceException e, HttpServletRequest request){
        log.info("Jpa 사용 간 에러 발생!! 발생 위치: {}\n", e.getFrom(), e.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.createErrorResponse(
                                CustomErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURI()));
    }

    @ExceptionHandler( NoAuthorizationException.class )
    public ResponseEntity<ErrorResponse> handleNoAuthorizationException(
            CustomJpaPersistenceException e, HttpServletRequest request){
        log.info("Jpa 사용 간 에러 발생!! 발생 위치: {}\n", e.getFrom(), e.getCause());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ErrorResponse.createErrorResponse(
                                CustomErrorCode.FORBIDDEN, request.getRequestURI()));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(
            InternalServerException e, HttpServletRequest request) {
        log.info("서버 내부 예외 발생!! 발생 위치 : {}\n", e.getFrom(), e.getCause());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.createErrorResponse(
                                CustomErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(
            Exception e, HttpServletRequest request) {
        log.warn("Unhandled Exception 발생!!", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.createErrorResponse(
                                CustomErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURI()
                        )
                );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(
            Exception e, HttpServletRequest request) {
        log.warn("Illegal Argument!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}




