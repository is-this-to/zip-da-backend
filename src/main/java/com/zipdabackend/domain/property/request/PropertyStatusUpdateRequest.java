package com.zipdabackend.domain.property.request;

import com.zipdabackend.global.constant.PropertyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyStatusUpdateRequest {
    @NotNull(message = "거래 상태를 선택해 주세요.")
    private PropertyStatus Status;
}
