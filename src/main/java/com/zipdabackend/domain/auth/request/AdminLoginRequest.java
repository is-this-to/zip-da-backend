package com.zipdabackend.domain.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminLoginRequest(
        @NotBlank(message = "관리자 코드는 필수 항목입니다.")
        @Size(max = 20, message = "길이가 허용 범위를 초과했습니다.")
        String adminCode,

        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        @Size(max = 100, message = "비밀번호 길이가 허용 범위를 초과했습니다.")
        String password
) { }
