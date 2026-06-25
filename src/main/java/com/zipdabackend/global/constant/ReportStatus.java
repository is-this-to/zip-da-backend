package com.zipdabackend.global.constant;

import lombok.Getter;

@Getter
public enum ReportStatus {
    RECEIVED("접수"),
    REJECTED("반려"),
    DELETED("삭제");

    private final String description;

    ReportStatus(String description) {
        this.description = description;
    }
}
