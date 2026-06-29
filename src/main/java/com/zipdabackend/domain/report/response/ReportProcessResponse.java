package com.zipdabackend.domain.report.response;

import lombok.Builder;

@Builder
public record ReportProcessResponse(
    Long reportId
    ,String status
) {
}
