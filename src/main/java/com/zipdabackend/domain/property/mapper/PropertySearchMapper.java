package com.zipdabackend.domain.property.mapper;

import com.zipdabackend.domain.property.request.PropertySearchRequest;
import com.zipdabackend.domain.property.response.PropertySummaryResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertySearchMapper {
    List<PropertySummaryResponse> searchPagination(PropertySearchRequest propertySearchRequest);
    long searchTotal(PropertySearchRequest request);
}
