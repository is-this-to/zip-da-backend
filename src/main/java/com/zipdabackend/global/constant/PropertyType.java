package com.zipdabackend.global.constant;

import lombok.Getter;

@Getter
public enum PropertyType {
    ONE_ROOM("원룸"),
    TWO_ROOM("투룸"),
    OFFICETEL("오피스텔"),
    VILLA("빌라"),
    HOUSE("주택");

    private final String description;

    PropertyType(String description) {
        this.description = description;
    }
}
