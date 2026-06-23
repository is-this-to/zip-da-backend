package com.zipdabackend.domain.property.controller;

import com.zipdabackend.domain.property.request.PropertySearchRequest;
import com.zipdabackend.domain.property.response.PropertySearchResponse;
import com.zipdabackend.domain.property.service.PropertySearchService;
import com.zipdabackend.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PropertySearchController {
    private final PropertySearchService propertySearchService;

    @GetMapping("/properties")
    public ResponseEntity<GlobalResponse<PropertySearchResponse>> searchProperties(PropertySearchRequest propertySearchRequest){
        PropertySearchResponse propertySearchResponse = propertySearchService.searchProperties(propertySearchRequest);

        return ResponseEntity.status(200).body(
                GlobalResponse.<PropertySearchResponse>builder()
                        .code("00")
                        .message("정상처리")
                        .data(propertySearchResponse)
                        .build()
        );
    }
}