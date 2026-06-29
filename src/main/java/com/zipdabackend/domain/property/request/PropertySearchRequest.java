package com.zipdabackend.domain.property.request;

import com.zipdabackend.global.constant.PropertyStatus;
import com.zipdabackend.global.constant.PropertyType;
import com.zipdabackend.global.constant.TransactionType;
import jakarta.validation.constraints.Min;

public record PropertySearchRequest(
        @Min(value = 1, message = "1이상 숫자만 허용합니다.")
        Integer page,
        @Min(value = 1, message = "1이상 숫자만 허용합니다.")
        Integer pageSize,

        PropertyType propertyType,
        TransactionType transactionType,
        PropertyStatus status,

        Long regionId,

        Long minPrice,
        Long maxPrice,
        Long minDeposit,
        Long maxDeposit,
        Long minMonthlyRent,
        Long maxMonthlyRent
) {
    public PropertySearchRequest{
        page = (page != null && page > 0)? page : 1;
        pageSize = (pageSize != null && pageSize > 0)? pageSize : 20;
        status = (status != null) ? status : PropertyStatus.FOR_SALE;
    }
    public int getOffset(){
        return (this.page -1) * this.pageSize;
    }

}


