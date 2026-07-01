package com.zipdabackend.domain.report.service;

import com.zipdabackend.domain.report.entity.Report;
import com.zipdabackend.domain.report.mapper.ReportMapper;
import com.zipdabackend.domain.report.request.ReportCreateRequest;
import com.zipdabackend.domain.report.request.ReportManageRequest;
import com.zipdabackend.domain.report.request.ReportProcessRequest;
import com.zipdabackend.domain.report.response.PageResponse;
import com.zipdabackend.domain.report.response.ReportCreateResponse;
import com.zipdabackend.domain.report.response.ReportManageResponse;
import com.zipdabackend.domain.report.response.ReportProcessResponse;
import com.zipdabackend.global.constant.ReportStatus;
import com.zipdabackend.global.error.custom.report.ReportAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;

    public PageResponse<ReportManageResponse> show(ReportManageRequest req) {
        // 페이지네이션
        int currentPage = req.page();
        int size = req.pageSize();
        int offset = (currentPage - 1) * size;

        long total = reportMapper.searchTotal();
        boolean lastPage = offset + size >= total;

        // 관리자 신고관리 목록
        List<ReportManageResponse> result = reportMapper.getReport(size, offset);

        return PageResponse.<ReportManageResponse>builder()
                .total(total)
                .currentPage(currentPage)
                .pageSize(size)
                .lastPage(lastPage)
                .dataList(result)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public ReportProcessResponse process(Long reportId, ReportProcessRequest reportProcessRequest) {
        String status = reportProcessRequest.status();
        Long propertyId = reportProcessRequest.propertyId();

        // 관리자 신고처리
        if(ReportStatus.DELETED.name().equals(status)) {
            // 매물 삭제
            reportMapper.deleteProperty(propertyId);
        }
        // 신고상태 변경
        reportMapper.updateStatus(reportId, status);

        return new ReportProcessResponse(reportId, status);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReportCreateResponse create(ReportCreateRequest reportCreateRequest) {
        // 동일 매물 중복 신고 방지(유저당 1회)
        Report alreadyReport = reportMapper.findByUserProperty(reportCreateRequest.userId(), reportCreateRequest.propertyId());

        if(alreadyReport != null) {
            throw new ReportAlreadyExistsException("같은 회원이 같은 매물 중복 신고입니다.");
        }

        // 신고하기
        Report newReport = new Report();
        newReport.setPropertyId(reportCreateRequest.propertyId());
        newReport.setUserId(reportCreateRequest.userId());
        newReport.setReportType(reportCreateRequest.reportType());
        newReport.setReason(reportCreateRequest.reason());
        newReport.setStatus(reportCreateRequest.status());
        reportMapper.insertReport(newReport);

        return new ReportCreateResponse(newReport.getReportId(), newReport.getStatus());
    }
}
