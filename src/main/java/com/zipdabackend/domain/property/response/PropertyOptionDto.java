package com.zipdabackend.domain.property.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyOptionDto {
    private Long optionId;
    private String optionName;
    private String optionValue;
}
