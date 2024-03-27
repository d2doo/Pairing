package com.ssafy.i10a709be.common.exception;

public class NoAuthorizationException extends RuntimeException{
    private final String from;

    public NoAuthorizationException(String message, Object clazz) {
        super(message);
        from = clazz.getClass().getName();
    }

    public NoAuthorizationException(String message, Throwable cause, Object clazz) {
        super(message, cause);
        from = clazz.getClass().getName();
    }

    public NoAuthorizationException(Throwable cause, Object clazz) {
        super(cause);
        from = clazz.getClass().getName();
    }
}
