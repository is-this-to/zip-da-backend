package com.zipdabackend.domain.admin.response;

import com.zipdabackend.global.constant.UserRole;
import lombok.Builder;

@Builder
public record AdminResponse(
        long adminId,
        String name,
        UserRole role
) { }
