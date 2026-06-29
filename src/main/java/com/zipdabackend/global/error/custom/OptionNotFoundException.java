package com.zipdabackend.global.error.custom;

public class OptionNotFoundException extends RuntimeException {
    public OptionNotFoundException(String message) {
        super(message);
    }
}
