package com.ssafy.i10a709be.common.exception;

public class MultiPartFileNotFoundException extends RuntimeException{
    private final String from;
    public MultiPartFileNotFoundException(String message, Object clazz) {
        super(message);
        from = clazz.getClass().getName();
    }

    public MultiPartFileNotFoundException(String message, Throwable cause, Object clazz) {
        super(message, cause);
        from = clazz.getClass().getName();
    }

    public MultiPartFileNotFoundException(Throwable cause, Object clazz) {
        super(cause);
        from = clazz.getClass().getName();
    }
}
