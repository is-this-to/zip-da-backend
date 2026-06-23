package com.zipdabackend.global.error.custom;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
