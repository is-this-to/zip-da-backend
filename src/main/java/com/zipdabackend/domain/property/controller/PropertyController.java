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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 *
 * ※ 인증 파트(한지윤 / feature/auth-user-agent_HJY) 연동 전까지
 *   currentUserId / currentUserRole 은 더미 값 반환.
 *   연동 후 @AuthenticationPrincipal 등으로 교체.
 */
@RestController
@RequestMapping("api/properties")
@RequiredArgsConstructor
public class PropertyController {
    private static final String SUCCESS_CODE = "00";
    private static final String SUCCESS_MESSAGE = "정상 처리";

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<GlobalResponse<PropertyCreateResponse>>create(
            @Valid @RequestBody PropertyCreateRequest request) {
        PropertyCreateResponse data = propertyService.create(
                request, currentUserId(), currentUserRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(ok(data));
    }

//    @GetMapping("/{propertyId}")
//    public ResponseEntity<GlobalResponse<PropertyDetailResponse>> detail(@PathVariable Long propertyId) {
//        return ResponseEntity.ok(ok(propertyService.getDetail(propertyId)));
//    }

    @PatchMapping("/{propertyId}")
    public ResponseEntity<GlobalResponse<PropertyDetailResponse>> update(
            @PathVariable Long propertyId,
            @Valid @RequestBody PropertyUpdateRequest request) {
        PropertyDetailResponse data = propertyService.update(
                propertyId, request, currentUserId(),currentUserRole());
        return ResponseEntity.ok(ok(data));
    }

    @PatchMapping("/{propertyId}/status")
    public ResponseEntity<GlobalResponse<PropertyStatusUpdateResponse>> changeStatus(
            @PathVariable Long propertyId,
            @Valid @RequestBody PropertyStatusUpdateRequest request) {
        PropertyStatusUpdateResponse data = propertyService.changeStatus(
                propertyId, request.getStatus(), currentUserId(), currentUserRole());
                return ResponseEntity.ok(ok(data));
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<GlobalResponse<Object>> delete(@PathVariable Long propertyId) {
        propertyService.delete(propertyId, currentUserId(), currentUserRole());
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
    /**
     * 현재 로그인 사용자 ID (임시).
     * 인증 연동 후 @AuthenticationPrincipal Long userId 로 교체.
     */
    private Long currentUserId() {
        return 1L; // 더미: user 테이블에 미리 넣어둔 테스트 계정 PK
    }

    /**
     * 현재 로그인 사용자 권한 (임시).
     * 인증 연동 후 principal 에서 role 추출하도록 교체.
     */
    private UserRole currentUserRole() {
        return UserRole.AGENT; // AGENT/ADMIN/USER 테스트 시 값 변경
    }

}






































