package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public enum PropertyStatus {
    FOR_SALE( "판매중"),
    COMPLETED("거래완료"),
    HIDDEN("숨김");

    private final String description;

    PropertyStatus(String description) {
        this.description = description;
    }
}
