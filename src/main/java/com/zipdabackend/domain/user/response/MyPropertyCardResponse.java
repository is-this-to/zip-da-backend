package com.zipdabackend.domain.user.response;

import com.zipdabackend.global.constant.PropertyStatus;
import com.zipdabackend.global.constant.PropertyType;
import com.zipdabackend.global.constant.TransactionType;

public record MyPropertyCardResponse(
        Long propertyId,
        String thumbnailUrl,
        PropertyType propertyType,
        TransactionType transactionType,
        Long price,
        Long deposit,
        Long monthlyRent,
        Double areaM2,
        Integer floor,
        Long maintenanceFee,
        PropertyStatus status,
        String regionName,
        String createdAt
) {
}
