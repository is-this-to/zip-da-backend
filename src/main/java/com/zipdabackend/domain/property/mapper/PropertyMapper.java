package com.zipdabackend.domain.property.mapper;

import com.zipdabackend.domain.property.entity.Property;
import com.zipdabackend.domain.property.entity.PropertyImage;
import com.zipdabackend.domain.property.response.PropertyDetailResponse;
import com.zipdabackend.global.constant.PropertyStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PropertyMapper {

    // ===== 등록 =====
    int insertProperty(Property property);
    int insertPropertyImages(@Param("images") List<PropertyImage> images);
    int insertPropertyOptions(@Param("propertyId")Long propertyId,
                              @Param("optionIds")List<Long> optionIds);

    // ===== 존재/권한 확인 =====
    /** user_id, status, deleted_at 등 권한·상태 검증용 경량 조회. 없으면 null. */
    Property selectById(@Param("propertyId")Long propertyId);
    int countRegion(@Param("regionId")Long regionId);
    int countOptions(@Param("optionIds")List<Long>optionIds);

    // ===== 상세 조회 (상세 + 수정 응답 공용) =====
    PropertyDetailResponse selectDetailById(@Param("propertyId")Long propertyId);
    Boolean selectIsFavorite(@Param("propertyId")Long propertyId, @Param("userId")Long userId);
    List<PropertyDetailResponse.Image> selectImages(@Param("propertyId")Long propertyId);
    List<PropertyDetailResponse.Option> selectOptions(@Param("propertyId")Long propertyId);

    // ===== 수정 =====
    int updateProperty(Property property); //동적 부분 수정
    int updateStatus(@Param("propertyId")Long propertyId,
                     @Param("status")PropertyStatus status);
    int deletePropertyOptions(@Param("propertyId")Long propertyId);

    // ===== 삭제 (soft delete) =====
    int softDelete(@Param("propertyId")Long propertyId);
    int softDeleteImages(@Param("propertyId")Long propertyId);
}
