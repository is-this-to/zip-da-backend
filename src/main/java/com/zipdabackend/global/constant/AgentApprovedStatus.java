package com.zipdabackend.global.constant;

import lombok.Getter;

@Getter
public enum AgentApprovedStatus {
    APPROVED("활동 중"),
    PENDING("심사 중"),
    REJECTED("거부됨");

    private final String description;

    AgentApprovedStatus(String description) {
        this.description = description;
    }
}