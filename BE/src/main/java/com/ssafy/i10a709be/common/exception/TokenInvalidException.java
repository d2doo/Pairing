package com.ssafy.i10a709be.common.exception;

public class TokenInvalidException extends CustomException{
    public TokenInvalidException() {
        super(CustomErrorCode.TOKEN_ERROR);
    }
}
