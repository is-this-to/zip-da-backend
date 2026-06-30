package com.zipdabackend.domain.agent.response;

import com.zipdabackend.global.constant.AgentApprovedStatus;
import lombok.Builder;

@Builder
public record AgentCheckInfo(
        String licenseNo,
        String businessNo,
        String officeName,
        AgentApprovedStatus approvedStatus,
        String agentImageUrl
) {
}
