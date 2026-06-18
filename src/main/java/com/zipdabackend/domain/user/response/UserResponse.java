package com.zipdabackend.domain.user.response;

import com.zipdabackend.global.constant.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(
        long userId
    , String email
    , String name
    , String nick
    , String phone
    , UserRole role
    , String createdAt
) {}
