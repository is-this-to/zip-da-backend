package com.zipdabackend.global.responses;

import lombok.Builder;

@Builder
public record GlobalResponse<T>(
        String code
        ,String message
        ,T data
) {
}
