package com.zipdabackend.global.error.custom;

public class RegionNotFoundException extends RuntimeException {
    public RegionNotFoundException(String message) {
        super(message);
    }
}
