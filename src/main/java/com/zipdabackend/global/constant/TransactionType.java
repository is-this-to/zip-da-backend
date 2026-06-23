package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public enum TransactionType {
    MONTHLY_RENT("월세"),
    JEONSE("전세"),
    SALE("매매"),
    SHORT_TERM( "단기");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }
}
