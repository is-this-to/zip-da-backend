package com.zipdabackend.domain.region.mapper;

import com.zipdabackend.domain.region.request.RegionSearchRequest;
import com.zipdabackend.domain.region.response.RegionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RegionMapper {
    List<RegionResponse> searchRegions(RegionSearchRequest request);

    List<String> searchProvinces();

    List<String> searchCities(String province);

    List<RegionResponse> searchDistricts(
            @Param("province") String province,
            @Param("city") String city
    );
}
