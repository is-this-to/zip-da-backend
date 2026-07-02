package com.zipdabackend.domain.report.controller;

import com.zipdabackend.domain.report.request.ReportCreateRequest;
import com.zipdabackend.domain.report.request.ReportManageRequest;
import com.zipdabackend.domain.report.request.ReportProcessRequest;
import com.zipdabackend.domain.report.response.PageResponse;
import com.zipdabackend.domain.report.response.ReportCreateResponse;
import com.zipdabackend.domain.report.response.ReportManageResponse;
import com.zipdabackend.domain.report.response.ReportProcessResponse;
import com.zipdabackend.domain.report.service.ReportService;
import com.zipdabackend.global.response.GlobalResponse;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/admin/reports")
    public ResponseEntity<GlobalResponse<PageResponse<ReportManageResponse>>> show(
        ReportManageRequest req
    ) {
        PageResponse<ReportManageResponse> result = reportService.show(req);

        return ResponseEntity.status(200).body(
                GlobalResponse.<PageResponse<ReportManageResponse>>builder()
                        .code("00")
                        .message("정상 처리")
                        .data(result)
                        .build()
        );
    }

    @PatchMapping("/admin/reports/{reportId}")
    public ResponseEntity<GlobalResponse<ReportProcessResponse>> process(
            @PathVariable Long reportId,
            @RequestBody ReportProcessRequest reportProcessRequest
    ) {
        ReportProcessResponse result = reportService.process(reportId, reportProcessRequest);

        return ResponseEntity.status(200).body(
                GlobalResponse.<ReportProcessResponse>builder()
                        .code("00")
                        .message("정상 처리")
                        .data(result)
                        .build()
        );
    }

    @PostMapping("/reports")
    public ResponseEntity<GlobalResponse<ReportCreateResponse>> create(
            @AuthenticationPrincipal Claims claims,
            @Valid @RequestBody ReportCreateRequest reportCreateRequest
    ) {
        Long loginUserId = Long.parseLong(claims.getSubject());

        ReportCreateResponse result = reportService.create(loginUserId, reportCreateRequest);

        return ResponseEntity.status(200).body(
                GlobalResponse.<ReportCreateResponse>builder()
                        .code("00")
                        .message("정상 처리")
                        .data(result)
                        .build()
        );
    }
}
