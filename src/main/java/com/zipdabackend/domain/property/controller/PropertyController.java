package com.zipdabackend.domain.property.controller;

import com.zipdabackend.domain.property.request.PropertyCreateRequest;
import com.zipdabackend.domain.property.request.PropertyStatusUpdateRequest;
import com.zipdabackend.domain.property.request.PropertyUpdateRequest;
import com.zipdabackend.domain.property.response.PropertyCreateResponse;
import com.zipdabackend.domain.property.response.PropertyDetailResponse;
import com.zipdabackend.domain.property.response.PropertyStatusUpdateResponse;
import com.zipdabackend.domain.property.service.PropertyService;
import com.zipdabackend.global.constant.UserRole;
import com.zipdabackend.global.response.GlobalResponse;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 매물 컨트롤러 (담당: 임호탁 / feature/property_LHT)
 *
 *  - POST   /api/properties              매물 등록   (PROPERTY01)
 *  - GET    /api/properties/{id}         매물 상세   (PROPERTY04)
 *  - PATCH  /api/properties/{id}         매물 수정   (PROPERTY03 EXECUTE01, 부분수정)
 *  - PATCH  /api/properties/{id}/status  거래상태   (PROPERTY03 EXECUTE02)
 *  - DELETE /api/properties/{id}         매물 삭제   (PROPERTY02, soft delete)
 *
 * 응답은 모두 GlobalResponse 로 감쌈 (성공: code "00", message "정상 처리").
 */
@RestController
@RequestMapping("api/properties")
@RequiredArgsConstructor
public class PropertyController {
    private static final String SUCCESS_CODE = "00";
    private static final String SUCCESS_MESSAGE = "정상 처리";

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<GlobalResponse<PropertyCreateResponse>> create(
            @Valid @RequestBody PropertyCreateRequest request,
            @AuthenticationPrincipal Claims claims) {
        PropertyCreateResponse data = propertyService.create(
                request, getUserId(claims), getUserRole(claims));
        return ResponseEntity.status(HttpStatus.CREATED).body(ok(data));
    }

//    @GetMapping("/{propertyId}")
//    public ResponseEntity<GlobalResponse<PropertyDetailResponse>> detail(@PathVariable Long propertyId) {
//        return ResponseEntity.ok(ok(propertyService.getDetail(propertyId)));
//    }

    @PatchMapping("/{propertyId}")
    public ResponseEntity<GlobalResponse<PropertyDetailResponse>> update(
            @PathVariable Long propertyId,
            @Valid @RequestBody PropertyUpdateRequest request,
            @AuthenticationPrincipal Claims claims) {
        PropertyDetailResponse data = propertyService.update(
                propertyId, request, getUserId(claims), getUserRole(claims));
        return ResponseEntity.ok(ok(data));
    }

    @PatchMapping("/{propertyId}/status")
    public ResponseEntity<GlobalResponse<PropertyStatusUpdateResponse>> changeStatus(
            @PathVariable Long propertyId,
            @Valid @RequestBody PropertyStatusUpdateRequest request,
            @AuthenticationPrincipal Claims claims) {
        PropertyStatusUpdateResponse data = propertyService.changeStatus(
                propertyId, request.getStatus(), getUserId(claims), getUserRole(claims));
        return ResponseEntity.ok(ok(data));
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<GlobalResponse<Object>> delete(
            @PathVariable Long propertyId,
            @AuthenticationPrincipal Claims claims) {
        propertyService.delete(propertyId, getUserId(claims), getUserRole(claims));
        return ResponseEntity.ok(ok(null));
    }

    // ============ 내부 헬퍼 ============

    private <T> GlobalResponse<T> ok(T data) {
        return GlobalResponse.<T>builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .data(data)
                .build();
    }

    private Long getUserId(Claims claims) {
        return Long.parseLong(claims.getSubject());
    }

    private UserRole getUserRole(Claims claims) {
        return UserRole.valueOf(claims.get("role", String.class));
    }
}