package com.zipdabackend.domain.report.request;

import com.zipdabackend.global.constant.ReportStatus;
import com.zipdabackend.global.constant.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReportCreateRequest(
        @NotNull(message = "매물아이디는 필수입니다.")
        Long propertyId,

        @NotNull(message = "사용자아이디는 필수입니다.")
        Long userId,

        @NotNull(message = "신고 유형을 선택해주세요.")
        ReportType reportType,

        String reason,

        @NotNull(message = "신고상태는 필수입니다.")
        ReportStatus status
) {
}
