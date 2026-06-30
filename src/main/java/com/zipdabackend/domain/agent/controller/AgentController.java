package com.zipdabackend.domain.agent.controller;

import com.zipdabackend.domain.agent.request.AgentApplyRequest;
import com.zipdabackend.domain.agent.response.AgentApplyResponse;
import com.zipdabackend.domain.agent.response.AgentCheckInfo;
import com.zipdabackend.domain.agent.service.AgentService;
import com.zipdabackend.global.response.GlobalResponse;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    @PostMapping()
    public ResponseEntity<GlobalResponse<AgentApplyResponse>> applyAgent(@Valid @RequestBody AgentApplyRequest agentApplyRequest, @AuthenticationPrincipal Claims claims) {
        return ResponseEntity.status(201).body(
                GlobalResponse.<AgentApplyResponse>builder()
                        .code("00")
                        .message("정상 처리")
                        .data(agentService.applyAgent(Long.parseLong(claims.getSubject()), agentApplyRequest))
                        .build()
        );
    }

    @GetMapping("/me")
    public ResponseEntity<GlobalResponse<AgentCheckInfo>> checkAgentInfo(@AuthenticationPrincipal Claims claims) {
        return ResponseEntity.status(201).body(
                GlobalResponse.<AgentCheckInfo>builder()
                        .code("00")
                        .message("정상 처리")
                        .data(agentService.checkAgentInfo(Long.parseLong(claims.getSubject())))
                        .build()
        );
    }
}
