package com.zipdabackend.global.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserRole {
    USER("USER", "일반 사용자"),
    AGENT("AGENT", "중개사"),
    ADMIN("ADMIN", "관리자");

    private final String code;
    private final String description;

    UserRole(String code, String description) {
        this.code = code;
        this.description = description;
    }
}