package com.zipdabackend.global.constant;

import lombok.Getter;

@Getter
public enum SourceType {
    DIRECT("직거래"),
    AGENT("공인중개사");

    private final String description;

    SourceType(String description) {
        this.description = description;
    }
}
