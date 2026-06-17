package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PropertyStatus {
    FOR_SALE("FOR_SALE", "판매중"),
    COMPLETED("COMPLETED", "거래완료"),
    HIDDEN("HIDDEN", "숨김");

    private final String code;
    private final String description;

    PropertyStatus(String code, String description) {
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
