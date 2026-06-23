package com.zipdabackend.domain.agent.mapper;

import com.zipdabackend.domain.agent.entity.Agent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AgentMapper {
    int insertAgent(Agent agent);
    Agent findActiveApplyByUserId(long userId);
}
