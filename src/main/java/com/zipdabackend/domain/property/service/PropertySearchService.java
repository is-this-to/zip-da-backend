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

        List<PropertySummaryResponse> properties = propertySearchMapper.searchPagination(propertySearchRequest);
        long total = propertySearchMapper.searchTotal(propertySearchRequest);

        boolean lastPage = propertySearchRequest.getOffset() + propertySearchRequest.pageSize() >= total;

        return PropertySearchResponse.builder()
                .total(total)
                .currentPage(propertySearchRequest.page())
                .pageSize(propertySearchRequest.pageSize())
                .lastPage(lastPage)
                .properties(properties)
                .build();
    }

}
