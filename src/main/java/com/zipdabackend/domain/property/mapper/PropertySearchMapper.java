package com.zipdabackend.domain.property.mapper;

import com.zipdabackend.domain.property.response.PropertySummaryResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertySearchMapper {
    List<PropertySummaryResponse> searchPagination(int size, int offset);
    long searchTotal();
}
