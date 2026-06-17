package com.zipdabackend.domain.auth.service;

import com.zipdabackend.domain.auth.mapper.AuthMapper;
import com.zipdabackend.domain.auth.request.RegistrationRequest;
import com.zipdabackend.domain.user.entity.User;
import com.zipdabackend.domain.user.mapper.UserMapper;
import com.zipdabackend.global.error.custom.DuplicateEmailException;
import com.zipdabackend.global.error.custom.DuplicateNickException;
import com.zipdabackend.global.error.custom.DuplicateUserException;
import com.zipdabackend.global.error.custom.UserRegistrationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    public void registration(RegistrationRequest registrationRequest) {
        User findByEmailUser = userMapper.findByEmail(registrationRequest.email());
        User findByNickUser = userMapper.findByNick(registrationRequest.nick());
        if(findByEmailUser != null) {
            throw new DuplicateEmailException("이미 가입된 이메일입니다.");
        }
        if(findByNickUser != null) {
            throw new DuplicateNickException("이미 사용된 닉네임입니다.");
        }
        User newUser = User.builder()
                .name(registrationRequest.name())
                .phone(registrationRequest.phone())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .email(registrationRequest.email())
                .nick(registrationRequest.nick())
                .build();

        try {
            int insertUserNum = authMapper.insertUser(newUser);

            if (insertUserNum != 1) {
                throw new UserRegistrationFailedException("회원가입 처리에 실패했습니다.");
            }
        // 조회하는 사이에 동일한 이메일, 닉네임을 가진 새로운 유저가 가입함
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserException("이미 가입된 이메일 또는 닉네임입니다.");
        }
    }
}
