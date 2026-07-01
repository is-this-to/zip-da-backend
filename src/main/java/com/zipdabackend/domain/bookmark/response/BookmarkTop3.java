package com.zipdabackend.domain.bookmark.response;

import lombok.Builder;

@Builder
public record BookmarkTop3(
        long propertyId,
        String thumbnailUrl,
        Long price,
        Long deposit,
        Long monthlyRent,
        long bookmarkCount
) {
}
