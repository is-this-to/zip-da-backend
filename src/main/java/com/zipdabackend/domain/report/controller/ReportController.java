package com.zipdabackend.domain.report.controller;

import com.zipdabackend.domain.report.request.ReportManageRequest;
import com.zipdabackend.domain.report.response.PageResponse;
import com.zipdabackend.domain.report.response.ReportManageResponse;
import com.zipdabackend.domain.report.service.ReportService;
import com.zipdabackend.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
