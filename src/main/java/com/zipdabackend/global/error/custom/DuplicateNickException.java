package com.zipdabackend.global.error.custom;

public class DuplicateNickException extends RuntimeException {
    public DuplicateNickException(String message) {
        super(message);
    }
}
