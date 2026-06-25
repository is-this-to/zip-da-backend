package com.zipdabackend.domain.property.request;

import com.zipdabackend.global.constant.PropertyType;
import com.zipdabackend.global.constant.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PropertyCreateRequest {

    @NotBlank(message = "매물 설명을 입력해 주세요.")
    private String description;

    @NotNull(message = "매물 유형을 선택해 주세요.")
    private PropertyType propertyType;

    @NotNull(message = "거래 유형을 선택해 주세요.")
    private TransactionType transactionType;

    @PositiveOrZero(message = "매매가는 0 이상이어야 합니다.")
    private Long price;

    @PositiveOrZero(message = "보증금은 0 이상이어야 합니다.")
    private Long deposit;

    @PositiveOrZero(message = "월세는 0 이상이어야 합니다.")
    private Long monthlyRent;

    @PositiveOrZero(message = "관리비는 0 이상이어야 합니다.")
    private Long maintenanceFee;

    @NotNull(message = "지역을 선택해 주세요.")
    private Long regionId;

    @NotBlank(message = "상세 주소를 입력해주세요.")
    private String detailAddress;

    private BigDecimal areaM2;
    private Integer roomCount;
    private Integer bathroomCount;
    private Integer floor;
    private LocalDate moveInDate;

    private List<Long> optionIds;
    private List<String> imageUrls;
}
