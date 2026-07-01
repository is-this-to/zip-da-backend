package com.zipdabackend.domain.user.response;

import com.zipdabackend.global.constant.UserRole;
import lombok.Builder;

@Builder
public record MyProfileResponse(
        Long userId,
        String email,
        String name,
        String nick,
        String phone,
        UserRole role,
        String profileImageUrl,
        String createdAt
) {
}
