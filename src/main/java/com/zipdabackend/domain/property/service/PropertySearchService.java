package com.zipdabackend.domain.property.service;


import com.zipdabackend.domain.property.mapper.PropertySearchMapper;
import com.zipdabackend.domain.property.request.PropertySearchRequest;
import com.zipdabackend.domain.property.response.PropertySearchResponse;
import com.zipdabackend.domain.property.response.PropertySummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertySearchService {
    private final PropertySearchMapper propertySearchMapper;

    public PropertySearchResponse searchProperties(PropertySearchRequest propertySearchRequest){
        int currentPage = propertySearchRequest.page();
        int size = propertySearchRequest.pageSize();
        int offset = (currentPage - 1) * size;


        List<PropertySummaryResponse> properties = propertySearchMapper.searchPagination(size, offset);
        long total = propertySearchMapper.searchTotal();
        boolean lastPage = offset + size >= total;

        return PropertySearchResponse.builder()
                .total(total)
                .currentPage(currentPage)
                .pageSize(size)
                .lastPage(lastPage)
                .properties(properties)
                .build();
    }

}
