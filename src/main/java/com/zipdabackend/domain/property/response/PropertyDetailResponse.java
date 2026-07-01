package com.zipdabackend.domain.property.response;

import com.zipdabackend.global.constant.PropertyStatus;
import com.zipdabackend.global.constant.PropertyType;
import com.zipdabackend.global.constant.SourceType;
import com.zipdabackend.global.constant.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PropertyDetailResponse {

    private Long propertyId;
    private Long userId;
    private String description;
    private PropertyType propertyType;
    private TransactionType transactionType;
    private Long price;
    private Long deposit;
    private Long monthlyRent;
    private Long maintenanceFee;
    private Region region;
    private String detailAddress;
    private BigDecimal areaM2;
    private Integer roomCount;
    private Integer bathroomCount;
    private Integer floor;
    private LocalDate moveInDate;
    private SourceType sourceType;
    private PropertyStatus status;
    private String propertyUserNick;
    private String agentImageUrl;
    private String propertyUserRole;
    private Boolean isFavorite;
    private List<Image> images;
    private List<Option> options;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Region {
        private Long regionId;
        private String province;
        private String city;
        private String district;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Image {
        private Long imageId;
        private String imageUrl;
        private Boolean isThumbnail;
        private Integer sortOrder;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Option {
        private Long optionId;
        private String optionName;
        private String optionValue;
    }
}