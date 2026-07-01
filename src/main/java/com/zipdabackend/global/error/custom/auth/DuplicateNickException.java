package com.zipdabackend.global.error.custom.auth;

public class DuplicateNickException extends RuntimeException {
    public DuplicateNickException(String message) {
        super(message);
    }
}
