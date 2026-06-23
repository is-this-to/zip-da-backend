package com.zipdabackend.domain.property.response;

import com.zipdabackend.global.constant.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PropertyShowResponse {
    private Long propertyId;
    private Long userId;
    private String description;
    private PropertyType propertyType;
    private Long apartmentId;
    private TransactionType transactionType;
    private Long price;
    private Long deposit;
    private Long monthlyRent;
    private Long maintenanceFee;
    private String regionId;
    private String detailAddress;
    private Long areaM2;
    private Long roomCount;
    private Long bathroomCount;
    private Long floor;
    private String moveInDate;
    private SourceType sourceType;
    private PropertyStatus status;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    private List<PropertyOptionDto> options;    // 매물 옵션 목록
    private List<String> imageUrls;             // 매물 이미지 목록
}
