package com.zipdabackend.global.error.custom.auth;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
