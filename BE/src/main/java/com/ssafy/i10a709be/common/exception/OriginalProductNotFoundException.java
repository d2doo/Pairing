package com.ssafy.i10a709be.common.exception;

import com.ssafy.i10a709be.domain.deal.service.DealServiceImpl;
import lombok.Getter;

@Getter
public class OriginalProductNotFoundException extends RuntimeException{
    private final String from;

    public OriginalProductNotFoundException(String message, Object clazz) {
        super(message);
        from = clazz.getClass().getName();
    }

    public OriginalProductNotFoundException(String message, Throwable cause, Object clazz) {
        super(message, cause);
        from = clazz.getClass().getName();
    }

    public OriginalProductNotFoundException(Throwable cause, Object clazz) {
        super(cause);
        from = clazz.getClass().getName();
    }
}
