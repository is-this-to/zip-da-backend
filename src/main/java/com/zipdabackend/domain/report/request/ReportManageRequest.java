package com.zipdabackend.domain.report.request;

import jakarta.validation.constraints.Min;

public record ReportManageRequest(
    // 페이지네이션
    @Min(value = 1, message = "1이상 숫자만 허용합니다.")
    Integer page,
    @Min(value = 1, message = "1이상 숫자만 허용합니다.")
    Integer pageSize
) {
    public ReportManageRequest(Integer page, Integer pageSize){
        // 페이지네이션
        this.page = (page != null && page > 0)? page : 1;
        this.pageSize = (pageSize != null && pageSize > 0)? pageSize : 20;
    }
}
