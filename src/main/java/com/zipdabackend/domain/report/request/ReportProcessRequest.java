package com.zipdabackend.domain.report.request;

public record ReportProcessRequest (
    String status
    ,Long propertyId
) {
}
