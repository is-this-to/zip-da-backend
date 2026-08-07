package com.zipdabackend.domain.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserPasswordUpdateRequest(

        @NotBlank(message = "현재 비밀번호는 필수 항목입니다.")
        String currentPassword,

        @NotBlank(message = "새 비밀번호는 필수 항목입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,20}$",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~20자로 입력해주세요."
        )
        String newPassword
) {
}