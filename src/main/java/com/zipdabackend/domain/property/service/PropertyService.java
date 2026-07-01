package com.zipdabackend.domain.property.service;

import com.zipdabackend.domain.property.entity.Property;
import com.zipdabackend.domain.property.entity.PropertyImage;
import com.zipdabackend.domain.property.mapper.PropertyMapper;
import com.zipdabackend.domain.property.request.PropertyCreateRequest;
import com.zipdabackend.domain.property.request.PropertyUpdateRequest;
import com.zipdabackend.domain.property.response.PropertyCreateResponse;
import com.zipdabackend.domain.property.response.PropertyDetailResponse;
import com.zipdabackend.domain.property.response.PropertyStatusUpdateResponse;
import com.zipdabackend.global.constant.PropertyStatus;
import com.zipdabackend.global.constant.SourceType;
import com.zipdabackend.global.constant.TransactionType;
import com.zipdabackend.global.constant.UserRole;
import com.zipdabackend.global.error.custom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 매물 서비스 (담당: 임호탁 / feature/property_LHT)
 *  - PROPERTY01 등록
 *  - PROPERTY02 삭제 (soft delete)
 *  - PROPERTY03 수정 / 거래상태 변경
 *  - PROPERTY04 상세 (수정 응답 공용)
 *
 * 현재 로그인 사용자(userId·role) 는 컨트롤러에서 받아서 넘긴다.
 * 인증 파트(한지윤) 연동 전까지는 컨트롤러에서 더미 값 전달.
 */
@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyMapper propertyMapper;

    // ============ 등록 (PROPERTY01) ============
    @Transactional
    public PropertyCreateResponse create(PropertyCreateRequest req, Long loginUserId, UserRole role) {
        validatePrice(req.getTransactionType(), req.getPrice(), req.getDeposit(), req.getMonthlyRent());
        validateRegion(req.getRegionId());
        validateOptions(req.getOptionIds());

        SourceType sourceType = (role == UserRole.AGENT) ? SourceType.AGENT : SourceType.DIRECT;

        Property property = Property.builder()
                .userId(loginUserId)
                .description(req.getDescription())
                .propertyType(req.getPropertyType())
                .transactionType(req.getTransactionType())
                .price(req.getPrice())
                .deposit(req.getDeposit())
                .monthlyRent(req.getMonthlyRent())
                .maintenanceFee(req.getMaintenanceFee())
                .regionId(req.getRegionId())
                .detailAddress(req.getDetailAddress())
                .areaM2(req.getAreaM2())
                .roomCount(req.getRoomCount())
                .bathroomCount(req.getBathroomCount())
                .floor(req.getFloor())
                .moveInDate(req.getMoveInDate())
                .sourceType(sourceType)
                .status(PropertyStatus.FOR_SALE)
                .build();

        propertyMapper.insertProperty(property);
        Long propertyId = property.getPropertyId();

        saveImages(propertyId, req.getImageUrls());
        saveOptions(propertyId, req.getOptionIds());

        Property saved = propertyMapper.selectById(propertyId);
        return PropertyCreateResponse.builder()
                .propertyId(propertyId)
                .userId(loginUserId)
                .sourceType(sourceType)
                .status(PropertyStatus.FOR_SALE)
                .createdAt(saved != null ? saved.getCreatedAt() : null)
                .build();
    }

    // ============ 수정 (PROPERTY03 - 부분 수정) ============
    @Transactional
    public PropertyDetailResponse update(Long propertyId, PropertyUpdateRequest req,
                                         Long loginUserId, UserRole role) {
        requireEditable(propertyId, loginUserId, role);

        if (req.getTransactionType() != null) {
            validatePrice(req.getTransactionType(), req.getPrice(), req.getDeposit(), req.getMonthlyRent());
        }
        if (req.getRegionId() != null) {
            validateRegion(req.getRegionId());
        }
        if (req.getOptionIds() != null && !req.getOptionIds().isEmpty()) {
            validateOptions(req.getOptionIds());
        }

        Property property = Property.builder()
                .propertyId(propertyId)
                .description(req.getDescription())
                .propertyType(req.getPropertyType())
                .transactionType(req.getTransactionType())
                .price(req.getPrice())
                .deposit(req.getDeposit())
                .monthlyRent(req.getMonthlyRent())
                .maintenanceFee(req.getMaintenanceFee())
                .regionId(req.getRegionId())
                .detailAddress(req.getDetailAddress())
                .areaM2(req.getAreaM2())
                .roomCount(req.getRoomCount())
                .bathroomCount(req.getBathroomCount())
                .floor(req.getFloor())
                .moveInDate(req.getMoveInDate())
                .build();
        propertyMapper.updateProperty(property);

        if (req.getOptionIds() != null) {
            propertyMapper.deletePropertyOptions(propertyId);
            saveOptions(propertyId, req.getOptionIds());
        }
        if (req.getImageUrls() != null) {
            propertyMapper.softDeleteImages(propertyId);
            saveImages(propertyId, req.getImageUrls());
        }

        return getDetail(propertyId, loginUserId);
    }

    // ============ 거래상태 변경 (PROPERTY03 EXECUTE02) ============
    @Transactional
    public PropertyStatusUpdateResponse changeStatus(Long propertyId, PropertyStatus status,
                                                     Long loginUserId, UserRole role) {
        requireEditable(propertyId, loginUserId, role);
        propertyMapper.updateStatus(propertyId, status);
        return PropertyStatusUpdateResponse.builder()
                .propertyId(propertyId)
                .status(status)
                .build();
    }

    // ============ 삭제 (PROPERTY02 - soft delete) ============
    @Transactional
    public void delete(Long propertyId, Long loginUserId, UserRole role) {
        Property target = requireEditable(propertyId, loginUserId, role);
        propertyMapper.softDelete(propertyId);
        propertyMapper.softDeleteImages(propertyId);

        if (role == UserRole.ADMIN) {
            // TODO: 어드민 삭제 시 신고자·작성자 알림 (알림 도메인 별도 파트)
            //  - 작성자: target.getUserId()
        }
    }

    // ============ 상세 조회 (PROPERTY04 / 수정 응답 공용) ============
    @Transactional(readOnly = true)
    public PropertyDetailResponse getDetail(Long propertyId, Long loginUserId) {
        PropertyDetailResponse detail = propertyMapper.selectDetailById(propertyId);
        if (detail == null) {
            throw new PropertyNotFoundException("매물을 찾을 수 없습니다.");
        }
        if (loginUserId != null) {
            // 로그인한 사용자만 찜 여부 조회
            detail.setIsFavorite(propertyMapper.selectIsFavorite(propertyId, loginUserId));
        } else {
            // 게스트 쨈 없음
            detail.setIsFavorite(false);
        }
        detail.setImages(propertyMapper.selectImages(propertyId));
        detail.setOptions(propertyMapper.selectOptions(propertyId));
        return detail;
    }

    // ============ 내부 헬퍼 ============

    /** 매물 존재·미삭제·권한(소유자 또는 어드민) 검증. 통과한 매물을 반환. */
    private Property requireEditable(Long propertyId, Long loginUserId, UserRole role) {
        Property p = propertyMapper.selectById(propertyId);
        if (p == null || p.getDeletedAt() != null) {
            throw new PropertyNotFoundException("매물을 찾을 수 없습니다.");
        }
        boolean isAdmin = (role == UserRole.ADMIN);
        if (!isAdmin && !p.getUserId().equals(loginUserId)) {
            throw new PropertyAccessDeniedException("본인이 등록한 매물만 처리할 수 있습니다.");
        }
        return p;
    }

    private void validatePrice(TransactionType type, Long price, Long deposit, Long monthlyRent) {
        switch (type) {
            case SALE -> require(price != null && price > 0, "매매가를 입력해 주세요.");
            case JEONSE -> require(deposit != null && deposit > 0, "전세 보증금을 입력해 주세요.");
            case MONTHLY_RENT -> require(deposit != null && monthlyRent != null && monthlyRent > 0,
                    "월세는 보증금과 월세 금액을 입력해 주세요.");
            case SHORT_TERM -> require(deposit != null && monthlyRent != null && monthlyRent > 0,
                    "단기 임대는 보증금과 임대료를 입력해 주세요.");
        }
    }

    private void validateRegion(Long regionId) {
        if (regionId != null && propertyMapper.countRegion(regionId) == 0) {
            throw new RegionNotFoundException("지역을 찾을 수 없습니다.");
        }
    }

    private void validateOptions(List<Long> optionIds) {
        if (optionIds != null && !optionIds.isEmpty()
                && propertyMapper.countOptions(optionIds) != optionIds.size()) {
            throw new OptionNotFoundException("존재하지 않는 옵션이 포함되어 있습니다.");
        }
    }

    private void saveImages(Long propertyId, List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) return;
        List<PropertyImage> images = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            images.add(PropertyImage.builder()
                    .propertyId(propertyId)
                    .imageUrl(imageUrls.get(i))
                    .isThumbnail(i == 0)
                    .sortOrder(i)
                    .build());
        }
        propertyMapper.insertPropertyImages(images);
    }

    private void saveOptions(Long propertyId, List<Long> optionIds) {
        if (optionIds == null || optionIds.isEmpty()) return;
        propertyMapper.insertPropertyOptions(propertyId, optionIds);
    }

    private void require(boolean condition, String message) {
        if (!condition) {
            throw new InvalidPropertyPriceException(message);
        }
    }
}