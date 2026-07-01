package com.zipdabackend.domain.property.service;

import com.zipdabackend.domain.property.mapper.PropertyShowMapper;
import com.zipdabackend.domain.property.response.PropertyOptionDto;
import com.zipdabackend.domain.property.response.PropertyShowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyShowService {
    private final PropertyShowMapper propertyShowMapper;

public PropertyShowResponse show(Long propertyId) {

        // 매물 기본 정보 조회
        PropertyShowResponse result = propertyShowMapper.findByPk(propertyId);

        if (result == null) {
            throw new IllegalArgumentException("존재하지 않거나 삭제된 매물입니다.");  //TODO:사용자정의예외로 바꾸기
        }

        // 매물 옵션 리스트 조회
        List<PropertyOptionDto> options = propertyShowMapper.getOptionsByPropertyId(propertyId);

        // 매물 이미지 URL 리스트 조회
        List<String> imageUrls = propertyShowMapper.getImagesByPropertyId(propertyId);

        result.setOptions(options);
        result.setImageUrls(imageUrls);

        return result;
    }
}
