package com.zipdabackend.domain.property.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyOption {
    private Long propertyId;
    private Long optionId;
    private String optionValue;
}
