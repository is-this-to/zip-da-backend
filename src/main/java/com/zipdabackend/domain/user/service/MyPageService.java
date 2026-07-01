package com.zipdabackend.domain.user.service;

import com.zipdabackend.domain.auth.mapper.AuthMapper;
import com.zipdabackend.domain.user.entity.User;
import com.zipdabackend.domain.user.mapper.UserMapper;
import com.zipdabackend.domain.user.mapper.UserProfileMapper;
import com.zipdabackend.domain.user.request.MyProfileUpdateRequest;
import com.zipdabackend.domain.user.request.UserPasswordUpdateRequest;
import com.zipdabackend.domain.user.request.UserWithdrawRequest;
import com.zipdabackend.domain.user.response.MyProfileResponse;
import com.zipdabackend.domain.user.response.MyPropertyCardResponse;
import com.zipdabackend.domain.user.response.MyReportResponse;
import com.zipdabackend.global.constant.UserRole;
import com.zipdabackend.global.error.custom.auth.DuplicateNickException;
import com.zipdabackend.global.error.custom.auth.NotRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    // 로그인한 회원의 내 정보를 조회한다.
    public MyProfileResponse getMyProfile(Long userId) {
        User user = userProfileMapper.findMyProfile(userId);

        if (user == null) {
            throw new NotRegisteredException("회원 정보를 찾을 수 없습니다.");
        }

        return toProfileResponse(user);
    }

    // 로그인한 회원의 이름/닉네임/휴대폰 번호를 수정한다.
    @Transactional
    public MyProfileResponse updateMyProfile(Long userId, MyProfileUpdateRequest request) {
        User user = userMapper.findByPk(userId);

        if (user == null) {
            throw new NotRegisteredException("회원 정보를 찾을 수 없습니다.");
        }

        // 닉네임은 unique 값이므로 다른 사람이 이미 쓰고 있으면 막는다.
        User sameNickUser = userMapper.findByNick(request.nick());
        if (sameNickUser != null && sameNickUser.getUserId() != userId) {
            throw new DuplicateNickException("이미 사용 중인 닉네임입니다.");
        }

        int updatedRow = userProfileMapper.updateProfile(userId, request);
        if (updatedRow != 1) {
            throw new IllegalStateException("내 정보 수정에 실패했습니다.");
        }

        // 일반 유저는 프로필 사진을 사용하지 않는다.
        // 공인중개사만 프로필 사진 URL을 agent_image 테이블에 저장한다.
        if (user.getRole() == UserRole.AGENT && request.profileImageUrl() != null) {
            saveAgentProfileImage(userId, request.profileImageUrl());
        }

        return getMyProfile(userId);
    }

    // 비밀번호 변경 기능
    @Transactional
    public void updatePassword(Long userId, UserPasswordUpdateRequest request) {
        User user = userMapper.findByPk(userId);

        if (user == null) {
            throw new NotRegisteredException("회원 정보를 찾을 수 없습니다.");
        }

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new NotRegisteredException("현재 비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.newPassword());
        int updatedRow = userProfileMapper.updatePassword(userId, encodedPassword);

        if (updatedRow != 1) {
            throw new IllegalStateException("비밀번호 변경에 실패했습니다.");
        }
    }

    // 회원탈퇴 기능
    @Transactional
    public void withdraw(Long userId, UserWithdrawRequest request) {
        User user = userMapper.findByPk(userId);

        if (user == null) {
            throw new NotRegisteredException("회원 정보를 찾을 수 없습니다.");
        }

        // request가 null이거나 password가 비어 있으면 비밀번호 확인 없이 soft delete만 처리한다.

        if (request != null && request.password() != null && !request.password().isBlank()) {
            if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                throw new NotRegisteredException("비밀번호가 일치하지 않습니다.");
            }
        }

        int deletedRow = userProfileMapper.softDeleteUser(userId);
        if (deletedRow != 1) {
            throw new IllegalStateException("회원탈퇴 처리에 실패했습니다.");
        }

        // 탈퇴한 회원의 refreshToken은 지운다.
        authMapper.updateRefreshToken(userId, null);
    }

    // 내가 올린 매물 목록 조회.
    public List<MyPropertyCardResponse> getMyProperties(Long userId) {
        return userProfileMapper.findMyProperties(userId);
    }

    // 내가 접수한 신고 내역 조회.
    public List<MyReportResponse> getMyReports(Long userId) {
        return userProfileMapper.findMyReports(userId);
    }

    // User 엔티티를 프론트 응답용 DTO로 바꾼다.
    private MyProfileResponse toProfileResponse(User user) {
        String profileImageUrl = null;

        if (user.getRole() == UserRole.AGENT) {
            profileImageUrl = userProfileMapper.findAgentProfileImageUrl(user.getUserId());
        }

        return MyProfileResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .nick(user.getNick())
                .phone(user.getPhone())
                .role(user.getRole())
                .profileImageUrl(profileImageUrl)
                .createdAt(user.getCreatedAt())
                .build();
    }

    // 공인중개사 프로필 사진 저장/수정 분기 처리.
    private void saveAgentProfileImage(Long userId, String profileImageUrl) {
        int imageCount = userProfileMapper.countAgentProfileImage(userId);

        if (imageCount == 0) {
            userProfileMapper.insertAgentProfileImage(userId, profileImageUrl);
            return;
        }

        userProfileMapper.updateAgentProfileImage(userId, profileImageUrl);
    }
}
