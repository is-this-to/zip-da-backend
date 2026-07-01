package com.zipdabackend.domain.user.request;

public record UserPasswordUpdateRequest(
        String currentPassword,
        String newPassword
) {
}
