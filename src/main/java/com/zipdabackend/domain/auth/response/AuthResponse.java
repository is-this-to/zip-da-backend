package com.zipdabackend.domain.auth.response;

import com.zipdabackend.domain.user.response.UserResponse;
import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken
        , UserResponse user
        ) {
}
