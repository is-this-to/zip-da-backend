package com.zipdabackend.global.error.custom;

public class InvalidPropertyPriceException extends RuntimeException {
    public InvalidPropertyPriceException(String message) {
        super(message);
    }
}
