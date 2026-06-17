package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReportStatus {
    RECEIVED("RECEIVED", "접수"),
    IN_PROGRESS("IN_PROGRESS", "처리중"),
    RESOLVED("RESOLVED", "완료");

    private final String code;
    private final String description;

    ReportStatus(String code, String description) {
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
