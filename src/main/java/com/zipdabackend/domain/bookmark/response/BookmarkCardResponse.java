package com.zipdabackend.domain.bookmark.response;

import com.zipdabackend.global.constant.PropertyType;
import com.zipdabackend.global.constant.TransactionType;

public record BookmarkCardResponse(
        Long propertyId, // 매물 상세 페이지로 이동할 때 필요한 매물 id
        String thumbnailUrl, // 카드 위에 보여줄 대표 이미지 주소
        PropertyType propertyType, // 매물 유형
        TransactionType transactionType,// 월세, 전세, 매매 같은 거래 유형
        Long price, // 매매가
        Long deposit, // 보증금
        Long monthlyRent, // 월세
        Double areaM2, // 면적
        Long floor, //
        Long maintenanceFee, // 관리비
        String regionName // 서울특별시 강남구 역삼동 같은 지역명
) {

}
