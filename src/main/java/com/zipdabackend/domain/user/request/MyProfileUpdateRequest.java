package com.zipdabackend.domain.user.request;

public record MyProfileUpdateRequest(
        String name,
        String nick,
        String phone,
        String profileImageUrl
) {
}
