package com.zipdabackend.domain.user.controller;

import com.zipdabackend.domain.user.request.MyProfileUpdateRequest;
import com.zipdabackend.domain.user.request.UserPasswordUpdateRequest;
import com.zipdabackend.domain.user.request.UserWithdrawRequest;
import com.zipdabackend.domain.user.response.MyProfileResponse;
import com.zipdabackend.domain.user.response.MyPropertyCardResponse;
import com.zipdabackend.domain.user.response.MyReportResponse;
import com.zipdabackend.domain.user.service.MyPageService;
import com.zipdabackend.global.response.GlobalResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyPageController {

    private final MyPageService myPageService;

    // 내 정보 조회
    @GetMapping("/users/me")
    public ResponseEntity<GlobalResponse<MyProfileResponse>> getMyProfile(
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = Long.parseLong(claims.getSubject());

        return ResponseEntity.status(200).body(
                GlobalResponse.<MyProfileResponse>builder()
                        .code("00")
                        .message("내 정보 조회 성공")
                        .data(myPageService.getMyProfile(userId))
                        .build()
        );
    }

    // 내 정보 수정
    @PatchMapping("/users/me")
    public ResponseEntity<GlobalResponse<MyProfileResponse>> updateMyProfile(
            @AuthenticationPrincipal Claims claims,
            @RequestBody MyProfileUpdateRequest request
    ) {
        Long userId = Long.parseLong(claims.getSubject());

        return ResponseEntity.status(200).body(
                GlobalResponse.<MyProfileResponse>builder()
                        .code("00")
                        .message("내 정보 수정 성공")
                        .data(myPageService.updateMyProfile(userId, request))
                        .build()
        );
    }

    // 비밀번호 변경: 백엔드 준비용. 프론트는 아직 버튼만 있음.
    @PatchMapping("/users/me/password")
    public ResponseEntity<GlobalResponse<Void>> updatePassword(
            @AuthenticationPrincipal Claims claims,
            @RequestBody UserPasswordUpdateRequest request
    ) {
        Long userId = Long.parseLong(claims.getSubject());
        myPageService.updatePassword(userId, request);

        return ResponseEntity.status(200).body(
                GlobalResponse.<Void>builder()
                        .code("00")
                        .message("비밀번호 변경 성공")
                        .build()
        );
    }

    // 회원탈퇴: 백엔드 준비용. 프론트는 아직 버튼만 있음.
    @DeleteMapping("/users/me")
    public ResponseEntity<GlobalResponse<Void>> withdraw(
            @AuthenticationPrincipal Claims claims,
            @RequestBody(required = false) UserWithdrawRequest request
    ) {
        Long userId = Long.parseLong(claims.getSubject());
        myPageService.withdraw(userId, request);

        return ResponseEntity.status(200).body(
                GlobalResponse.<Void>builder()
                        .code("00")
                        .message("회원탈퇴 성공")
                        .build()
        );
    }

    // 내가 올린 게시물 조회
    @GetMapping("/users/me/properties")
    public ResponseEntity<GlobalResponse<List<MyPropertyCardResponse>>> getMyProperties(
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = Long.parseLong(claims.getSubject());

        return ResponseEntity.status(200).body(
                GlobalResponse.<List<MyPropertyCardResponse>>builder()
                        .code("00")
                        .message("내가 올린 게시물 조회 성공")
                        .data(myPageService.getMyProperties(userId))
                        .build()
        );
    }

    // 내 신고 내역 조회
    @GetMapping("/users/me/reports")
    public ResponseEntity<GlobalResponse<List<MyReportResponse>>> getMyReports(
            @AuthenticationPrincipal Claims claims
    ) {
        Long userId = Long.parseLong(claims.getSubject());

        return ResponseEntity.status(200).body(
                GlobalResponse.<List<MyReportResponse>>builder()
                        .code("00")
                        .message("신고 내역 조회 성공")
                        .data(myPageService.getMyReports(userId))
                        .build()
        );
    }
}
