package com.zipdabackend.domain.property.response;

import com.zipdabackend.global.constant.PropertyStatus;
import com.zipdabackend.global.constant.SourceType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PropertyCreateResponse {
    private Long propertyId;
    private Long userId;
    private SourceType sourceType;
    private PropertyStatus status;
    private LocalDateTime createdAt;
}
