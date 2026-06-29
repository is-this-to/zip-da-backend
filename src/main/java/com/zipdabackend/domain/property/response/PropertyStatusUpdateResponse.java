package com.zipdabackend.domain.property.response;

import com.zipdabackend.global.constant.PropertyStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertyStatusUpdateResponse {
    private Long propertyId;
    private PropertyStatus status;
}
