package com.zipdabackend.domain.auth.response;

import lombok.Builder;

@Builder
public record AuthResponse<T>(
        String accessToken
        , T principal
) { }
