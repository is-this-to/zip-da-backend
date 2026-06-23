package com.zipdabackend.domain.property.response;

import lombok.Builder;

@Builder
public record PropertySummaryResponse(
        long propertyId,
        String thumbnailUrl,

        String propertyType,
        String transactionType,

        long price,
        long deposit,
        long monthlyRent,

        double areaM2,

        int floor,

        Long maintenanceFee,

        String regionName
) {
}