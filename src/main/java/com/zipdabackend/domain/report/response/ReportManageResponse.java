package com.zipdabackend.domain.report.response;

import lombok.Builder;

@Builder
public record ReportManageResponse(
        Long reportId
        ,String reportType
        ,String reason
        ,String status
        ,String address
        ,String reporter
        ,String reportDate
        ,Long countByProperty
        ,Long propertyId
        ,String propertyType
        ,Long areaM2
        ,Long floor
        ,String thumbnailUrl
) {
}
