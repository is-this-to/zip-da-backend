package com.zipdabackend.domain.property.request;

import com.zipdabackend.global.constant.PropertyType;
import com.zipdabackend.global.constant.TransactionType;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PropertyUpdateRequest {

    private String description;
    private PropertyType propertyType;
    private TransactionType transactionType;

    @PositiveOrZero(message = "매매가는 0 이상이어야 합니다.")
    private Long price;

    @PositiveOrZero(message = "보증금은 0 이상이어야합니다.")
    private Long deposit;

    @PositiveOrZero(message = "월세는 0 이상이어야합니다.")
    private Long monthlyRent;

    @PositiveOrZero(message = "관리비는 0 이상이어야합니다.")
    private Long maintenanceFee;

    private Long regionId;
    private String detailAddress;
    private BigDecimal areaM2;
    private Integer roomCount;
    private Integer bathroomCount;
    private Integer floor;
    private LocalDate moveInDate;

    private List<Long> optionIds;
    private List<String> imageUrls;
}
