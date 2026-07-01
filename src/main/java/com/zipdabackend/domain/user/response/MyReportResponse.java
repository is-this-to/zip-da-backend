package com.zipdabackend.domain.user.response;

import com.zipdabackend.global.constant.ReportStatus;

public record MyReportResponse(
        Long reportId,
        Long propertyId,
        String reason,
        ReportStatus status,
        String createdAt
) {
}
