package com.zipdabackend.domain.property.request;

import jakarta.validation.constraints.Min;

public record PropertySearchRequest(
        @Min(value = 1, message = "1이상 숫자만 허용합니다.")
        Integer page,
        @Min(value = 1, message = "1이상 숫자만 허용합니다.")
        Integer pageSize
) {
    public PropertySearchRequest(Integer page, Integer pageSize){
        this.page = (page != null && page > 0)? page : 1;
        this.pageSize = (pageSize != null && pageSize > 0)? pageSize : 20;
    }
}
