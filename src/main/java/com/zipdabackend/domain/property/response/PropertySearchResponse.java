package com.zipdabackend.domain.property.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PropertySearchResponse(
        long total
        , int currentPage
        , int pageSize
        , boolean lastPage
        , List<PropertySummaryResponse> properties
) {
}
