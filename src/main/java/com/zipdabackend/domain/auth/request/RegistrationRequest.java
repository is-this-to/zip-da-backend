package com.zipdabackend.domain.auth.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegistrationRequest(
        @NotBlank(message = "이메일은 필수 항목입니다")
        @Pattern(regexp = "^[0-9a-zA-Z](?!.*?[\\-_.]{2})[a-zA-Z0-9\\-_.]{3,63}@[0-9a-zA-Z](?!.*?[\\-_.]{2})[a-zA-Z0-9\\-_.]{3,63}\\.[a-zA-Z]{2,3}$", message = "허용하지 않는 양식입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 항목입니다")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,20}$",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~20자로 입력해주세요."
        )
        String password,

        @NotBlank(message = "비밀번호 체크는 필수 항목입니다")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,20}$",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~20자로 입력해주세요."
        )
        String passwordCk,

        @NotBlank(message = "이름은 필수 항목입니다.")
        @Pattern(regexp = "^[a-zA-Z가-힣]{2,40}$", message = "영문 대소문자와 한글만 사용하여 2~40자로 입력해주세요.")
        String name,

        @NotBlank(message = "닉네임은 필수 항목입니다")
        @Pattern(regexp = "^[0-9a-zA-Z가-힣]{2,20}$", message = "숫자, 영문 대소문자와 한글만 사용하여 2~20자로 입력해주세요.")
        String nick,

        @Pattern(regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
        String phone

) {
    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치하지 않습니다")
    public boolean isPasswordMatch() {
        if(this.password == null || this.passwordCk == null){
            return false;
        }
        return this.password.equals(this.passwordCk);
    }
}
