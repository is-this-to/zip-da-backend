package com.zipdabackend.domain.property.entity;

import com.zipdabackend.global.constant.PropertyStatus;
import com.zipdabackend.global.constant.PropertyType;
import com.zipdabackend.global.constant.SourceType;
import com.zipdabackend.global.constant.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    private Long propertyId;
    private Long userId;
    private String description;
    private PropertyType propertyType;
    private Long apartmentId; // 추후 FK (현재 미사용)
    private TransactionType transactionType;

    private Long price;
    private Long deposit;
    private Long monthlyRent;
    private Long maintenanceFee;

    private Long regionId;
    private String detailAddress;

    private BigDecimal areaM2;
    private Integer roomCount;
    private Integer bathroomCount;
    private Integer floor;
    private LocalDate moveInDate;

    private SourceType sourceType;
    private PropertyStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
