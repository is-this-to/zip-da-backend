package com.zipdabackend.domain.property.mapper;

import com.zipdabackend.domain.property.response.PropertyOptionDto;
import com.zipdabackend.domain.property.response.PropertyShowResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyShowMapper {
    PropertyShowResponse findByPk(Long propertyId); // 매물 기본 조회
    List<PropertyOptionDto> getOptionsByPropertyId(Long propertyId);    // 매물 옵션 조회
    List<String> getImagesByPropertyId(Long propertyId);    // 매물 이미지 조회
}
