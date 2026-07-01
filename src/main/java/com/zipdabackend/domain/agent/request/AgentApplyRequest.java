package com.zipdabackend.domain.agent.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AgentApplyRequest(
    @NotBlank(message = "공인중개사 자격번호는 필수입니다.")
    @Pattern(
            regexp = "^(?=(?:.*\\d){8,14}$)[0-9-]+$",
            message = "공인중개사 자격번호는 숫자 8~14자리와 하이픈만 입력할 수 있습니다."
    )
    String licenseNo,

    @NotBlank(message = "사업자등록번호는 필수입니다.")
    @Pattern(
            regexp = "^\\d{3}-?\\d{2}-?\\d{5}$",
            message = "사업자등록번호는 숫자 10자리 또는 000-00-00000 형식이어야 합니다."
    )
    String businessNo,

    @NotBlank(message = "중개사무소명은 필수입니다.")
    @Size(max = 100, message = "중개사무소명은 100자 이하로 입력해 주세요.")
    String officeName,

    String agentImageUrl
) {}
