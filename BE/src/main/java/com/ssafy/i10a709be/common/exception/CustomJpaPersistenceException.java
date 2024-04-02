package com.ssafy.i10a709be.common.exception;


import jakarta.persistence.PersistenceException;
import lombok.Getter;

@Getter
public class CustomJpaPersistenceException extends PersistenceException {
    private final String from;

    public CustomJpaPersistenceException(String message, Object clazz) {
        super(message);
        from = clazz.getClass().getName();
    }

    public CustomJpaPersistenceException(String message, Throwable cause, Object clazz) {
        super(message, cause);
        from = clazz.getClass().getName();
    }

    public CustomJpaPersistenceException(Throwable cause, Object clazz) {
        super(cause);
        from = clazz.getClass().getName();
    }
}
