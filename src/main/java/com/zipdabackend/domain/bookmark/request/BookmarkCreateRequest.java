package com.zipdabackend.domain.bookmark.request;

public record BookmarkCreateRequest(
        Long userId, // 임시 로그인 데이터
        Long propertyId // 임시 매물id
) {
}
