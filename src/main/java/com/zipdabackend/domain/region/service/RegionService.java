package com.zipdabackend.domain.region.service;

import com.zipdabackend.domain.region.mapper.RegionMapper;
import com.zipdabackend.domain.region.request.RegionSearchRequest;
import com.zipdabackend.domain.region.response.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionMapper regionMapper;

    public List<RegionResponse> searchRegions(RegionSearchRequest request){
        return regionMapper.searchRegions(request);
    }

    public List<String> searchProvinces(){
        return regionMapper.searchProvinces();
    }

    public List<String> searchCities(String province){
        return regionMapper.searchCities(province);
    }

    public List<RegionResponse> searchDistricts(String province, String city){
        return regionMapper.searchDistricts(province,city);
    }
}
