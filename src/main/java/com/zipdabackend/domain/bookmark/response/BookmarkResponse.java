package com.zipdabackend.domain.bookmark.response;


public record BookmarkResponse(
    long propertyId,
    boolean isFavorite,
    long favoriteCount
) {
}
