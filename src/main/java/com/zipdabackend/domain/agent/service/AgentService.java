package com.zipdabackend.domain.agent.service;

import com.zipdabackend.domain.agent.entity.Agent;
import com.zipdabackend.domain.agent.mapper.AgentMapper;
import com.zipdabackend.domain.agent.request.AgentApplyRequest;
import com.zipdabackend.domain.agent.response.AgentApplyResponse;
import com.zipdabackend.domain.agent.response.AgentCheckInfo;
import com.zipdabackend.global.constant.AgentApprovedStatus;
import com.zipdabackend.global.error.custom.agent.AgentApplicationAlreadyExistsException;
import com.zipdabackend.global.error.custom.agent.AgentApplicationFailedException;
import com.zipdabackend.global.error.custom.agent.AgentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentMapper agentMapper;

    public AgentApplyResponse applyAgent(long userId, AgentApplyRequest agentApplyRequest) {
        Agent findByUserIdAgent = agentMapper.findActiveApplyByUserId(userId);

        if (findByUserIdAgent != null) {
            if(findByUserIdAgent.getApprovedStatus() == AgentApprovedStatus.APPROVED) {
                throw new AgentApplicationAlreadyExistsException("이미 공인중개사로 등록된 계정입니다.");
            }
            if(findByUserIdAgent.getApprovedStatus() == AgentApprovedStatus.PENDING) {
                throw new AgentApplicationAlreadyExistsException("현재 승인 대기 중입니다..");
            }
        }

        Agent newAgent = Agent.builder()
                .agentImageUrl(agentApplyRequest.agentImageUrl())
                .userId(userId)
                .businessNo(agentApplyRequest.businessNo())
                .licenseNo(agentApplyRequest.licenseNo())
                .officeName(agentApplyRequest.officeName())
                .build();

        int insertAgentNum = agentMapper.insertAgent(newAgent);

        if(insertAgentNum != 1) {
            throw new AgentApplicationFailedException("공인중개사 전환 신청 처리에 실패했습니다.");
        }

        return AgentApplyResponse.builder()
                    .agentId(newAgent.getAgentId())
                    .approvedStatus(AgentApprovedStatus.PENDING)
                    .userId(newAgent.getUserId())
                    .build();
    }

    public AgentCheckInfo checkAgentInfo(long userId) {
        Agent agent = agentMapper.findAgentByUserId(userId);
        if(agent == null) {
            throw new AgentNotFoundException("아직 공인중개사 신청을 하지 않았습니다.");
        }
        return AgentCheckInfo.builder()
                    .licenseNo(agent.getLicenseNo())
                    .officeName(agent.getOfficeName())
                    .businessNo(agent.getBusinessNo())
                    .approvedStatus(agent.getApprovedStatus())
                    .agentImageUrl(agent.getAgentImageUrl())
                    .build();
    }
}
