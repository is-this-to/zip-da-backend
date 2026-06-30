package com.zipdabackend.domain.admin.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Admin {
    long adminId;
    String adminCode;
    String password;
    String name;
    String refreshToken;
    String createdAt;
    String updatedAt;
    String deletedAt;
}
