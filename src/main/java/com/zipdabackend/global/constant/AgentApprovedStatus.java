package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AgentApprovedStatus {
    APPROVED("APPROVED", "활동 중"),
    PENDING("PENDING", "심사 중"),
    REJECTED("REJECTED", "거부됨");

    private final String code;
    private final String description;

    AgentApprovedStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
