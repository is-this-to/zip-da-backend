package com.zipdabackend.domain.agent.entity;

import com.zipdabackend.global.constant.AgentApprovedStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Agent {
    long agentId;
    long userId;
    String licenseNo;
    String businessNo;
    String officeName;
    AgentApprovedStatus approvedStatus;
    String agentImageUrl;
    String createdAt;
    String updatedAt;
    String deletedAt;
}
