package com.zipdabackend.domain.bookmark.request;

public record BookmarkCreateRequest(
    Long propertyId
) {
}
//프론트에서 전달하는 요청 데이터 propertyId를 담을 수 있는 DTO를 정의한 것
