package com.zipdabackend.global.error.custom;

public class UserRegistrationFailedException extends RuntimeException {
    public UserRegistrationFailedException(String message) {
        super(message);
    }
}
