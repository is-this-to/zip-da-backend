package com.zipdabackend.domain.region.controller;

import com.zipdabackend.domain.region.request.RegionSearchRequest;
import com.zipdabackend.domain.region.response.RegionResponse;
import com.zipdabackend.domain.region.service.RegionService;
import com.zipdabackend.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<RegionResponse>>> searchRegions(@ModelAttribute RegionSearchRequest request){
        List<RegionResponse> regions = regionService.searchRegions(request);

        return ResponseEntity.status(200).body(
                GlobalResponse.<List<RegionResponse>>builder()
                        .code("00")
                        .message("정상처리")
                        .data(regions)
                        .build()
        );
    }
    // 시도 조회
    @GetMapping("/provinces")
    public ResponseEntity<GlobalResponse<List<String>>> searchProvinces(){
        List<String> result = regionService.searchProvinces();

        return ResponseEntity.status(200).body(
                GlobalResponse.<List<String>>builder()
                        .code("00")
                        .message("정상처리")
                        .data(result)
                        .build()
        );
    }
    // 시군구 조회
    @GetMapping("/cities")
    public ResponseEntity<GlobalResponse<List<String>>> searchCities(
            @RequestParam String province
    ){
      if(province.isBlank()){
          throw new IllegalArgumentException("시도는 필수입니다.");
      }
      List<String> result = regionService.searchCities(province);

      return ResponseEntity.status(200).body(
              GlobalResponse.<List<String>>builder()
                      .code("00")
                      .message("정상처리")
                      .data(result)
                      .build()
      );
    }
    // 읍면동 조회
    @GetMapping("/districts")
    public ResponseEntity<GlobalResponse<List<RegionResponse>>> searchDistricts(
            @RequestParam String province,
            @RequestParam String city
    ){
        if(province.isBlank()){
            throw new IllegalArgumentException("시도는 필 수 입니다.");
        }
        if(city.isBlank()){
            throw new IllegalArgumentException("시군구는 필수입니다.");
        }
        List<RegionResponse> result = regionService.searchDistricts(province, city);

        return ResponseEntity.status(200).body(
                GlobalResponse.<List<RegionResponse>>builder()
                        .code("00")
                        .message("정상처리")
                        .data(result)
                        .build()
        );
    }
}
