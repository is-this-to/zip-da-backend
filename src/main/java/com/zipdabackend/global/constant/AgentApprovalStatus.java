package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public enum AgentApprovalStatus {
    PENDING( "승인대기"), // 중개사가 가입 후 서류(자격증 등) 검토를 기다리는 상태
    APPROVED( "승인완료"), // 관리자가 확인하여 활동을 허락한 상태
    REJECTED( "반려");    // 서류 미비, 자격 불충분 등으로 가입/권한을 거절한 상태

    private final String description;

    AgentApprovalStatus(String description) {
        this.description = description;
    }
}
