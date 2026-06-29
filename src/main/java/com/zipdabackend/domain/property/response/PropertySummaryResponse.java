package com.zipdabackend.domain.property.response;

import com.zipdabackend.global.constant.PropertyType;
import com.zipdabackend.global.constant.TransactionType;
import lombok.Builder;

@Builder
public record PropertySummaryResponse(
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

        String regionName
) {
}