package com.zipdabackend.global.error.custom.auth;

public class UserRegistrationFailedException extends RuntimeException {
    public UserRegistrationFailedException(String message) {
        super(message);
    }
}
