package com.zipdabackend.domain.report.entity;

import com.zipdabackend.global.constant.ReportStatus;
import com.zipdabackend.global.constant.ReportType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    private Long reportId;
    private Long propertyId;
    private Long userId;
    private ReportType reportType;
    private String reason;
    private ReportStatus status;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
