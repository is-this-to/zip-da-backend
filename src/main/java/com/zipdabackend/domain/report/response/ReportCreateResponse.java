package com.zipdabackend.domain.report.response;

import com.zipdabackend.global.constant.ReportStatus;
import lombok.Builder;

@Builder
public record ReportCreateResponse(
    Long reportId
    ,ReportStatus status
) {
}
