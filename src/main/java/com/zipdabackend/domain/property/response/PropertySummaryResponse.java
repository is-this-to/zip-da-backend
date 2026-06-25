package com.zipdabackend.domain.property.response;

import lombok.Builder;

@Builder
public record PropertySummaryResponse(
        long propertyId,
        String thumbnailUrl,

        String propertyType,
        String transactionType,

        Long price,
        Long deposit,
        Long monthlyRent,

        Double areaM2,

        Integer floor,

        Long maintenanceFee,

        String regionName
) {
}