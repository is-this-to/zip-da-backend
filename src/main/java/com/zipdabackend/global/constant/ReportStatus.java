package com.zipdabackend.global.constant;

import lombok.Getter;

@Getter
public enum ReportStatus {
    RECEIVED("접수"),
    IN_PROGRESS("처리중"),
    RESOLVED("완료");

    private final String description;

    ReportStatus(String description) {
        this.description = description;
    }
}
