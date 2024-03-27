package com.ssafy.i10a709be.domain.member.exception;

import com.ssafy.i10a709be.common.exception.CustomException;

public class TokenInvalidException extends CustomException {
    public TokenInvalidException() {
        super(MemberErrorCode.UNAUTHORIZED);
    }
}
