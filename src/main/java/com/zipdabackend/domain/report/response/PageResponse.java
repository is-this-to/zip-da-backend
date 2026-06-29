package com.zipdabackend.domain.report.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse<T>(
        long total
        , int currentPage
        , int pageSize
        , boolean lastPage
        , List<T> dataList
) {
}
