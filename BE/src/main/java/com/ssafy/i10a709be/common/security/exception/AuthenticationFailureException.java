package com.ssafy.i10a709be.common.security.exception;

import com.ssafy.i10a709be.common.exception.CustomException;

public class AuthenticationFailureException extends CustomException {
    public AuthenticationFailureException(){super(AuthenticationErrorCode.FORBIDDEN);};
}
