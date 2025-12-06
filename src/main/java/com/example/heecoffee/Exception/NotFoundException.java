package com.example.heecoffee.Exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String errorCode;

    public NotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
