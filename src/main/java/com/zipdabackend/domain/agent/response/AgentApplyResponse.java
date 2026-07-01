package com.zipdabackend.domain.agent.response;

import com.zipdabackend.global.constant.AgentApprovedStatus;
import lombok.Builder;

@Builder
public record AgentApplyResponse(
        long agentId,
        long userId,
        AgentApprovedStatus approvedStatus
) { }
