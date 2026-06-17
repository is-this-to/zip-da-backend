package com.zipdabackend.domain.user.response;

import lombok.Builder;

@Builder
public record UserResponse(
    long userId
    ,String email
    ,String name
    ,String nick
    ,String phone
    ,String role
    ,String profile
    ,String createdAt
) {}
