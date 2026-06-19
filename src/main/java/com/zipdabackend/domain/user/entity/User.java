package com.zipdabackend.domain.user.entity;

import com.zipdabackend.global.constant.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private long userId;
    private String email;
    private String password;
    private String name;
    private String nick;
    private String phone;
    private UserRole role;
    private String refreshToken;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
