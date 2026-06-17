package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PropertyType {
    ONE_ROOM("ONE_ROOM", "원룸"),
    TWO_ROOM("TWO_ROOM", "투룸"),
    OFFICETEL("OFFICETEL", "오피스텔"),
    VILLA("VILLA", "빌라"),
    HOUSE("HOUSE", "주택");

    private final String code;
    private final String description;

    PropertyType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
