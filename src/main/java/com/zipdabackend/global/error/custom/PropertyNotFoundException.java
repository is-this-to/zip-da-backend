package com.zipdabackend.global.error.custom;

import lombok.Getter;

@Getter
public class PropertyNotFoundException extends RuntimeException{
    public PropertyNotFoundException(String message) {
        super(message);
    }
}
