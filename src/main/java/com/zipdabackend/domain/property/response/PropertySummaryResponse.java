package com.zipdabackend.domain.property.response;

import lombok.Builder;

@Builder
public record PropertySummaryResponse(
        Long propertyId,
        String thumbnailUrl,

        String propertyType,
        String transactionType,

        Long price,
        Long deposit,
        Long monthlyRent,

        Double areaM2,

        Long floor,

        Long maintenanceFee,

        String regionName
) {
}