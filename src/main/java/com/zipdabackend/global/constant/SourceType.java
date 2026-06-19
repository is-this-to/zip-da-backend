package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SourceType {
    DIRECT("DIRECT", "직거래"),
    AGENT("AGENT", "공인중개사");

    private final String code;
    private final String description;

    SourceType(String code, String description) {
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
