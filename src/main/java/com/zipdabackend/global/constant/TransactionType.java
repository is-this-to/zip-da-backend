package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TransactionType {
    MONTHLY_RENT("MONTHLY_RENT", "월세"),
    JEONSE("JEONSE", "전세"),
    SALE("SALE", "매매"),
    SHORT_TERM("SHORT_TERM", "단기");

    private final String code;
    private final String description;

    TransactionType(String code, String description) {
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
