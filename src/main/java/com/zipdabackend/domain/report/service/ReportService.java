package com.zipdabackend.domain.report.service;

import com.zipdabackend.domain.report.mapper.ReportMapper;
import com.zipdabackend.domain.report.request.ReportManageRequest;
import com.zipdabackend.domain.report.response.PageResponse;
import com.zipdabackend.domain.report.response.ReportManageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        // 신고관리 목록
        List<ReportManageResponse> result = reportMapper.getReport(size, offset);

        return PageResponse.<ReportManageResponse>builder()
                .total(total)
                .currentPage(currentPage)
                .pageSize(size)
                .lastPage(lastPage)
                .dataList(result)
                .build();
    }
}
