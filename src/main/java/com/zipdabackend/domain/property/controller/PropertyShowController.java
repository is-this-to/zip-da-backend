package com.zipdabackend.domain.property.controller;

import com.zipdabackend.domain.property.response.PropertyShowResponse;
import com.zipdabackend.domain.property.service.PropertyShowService;
import com.zipdabackend.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PropertyShowController {
    private final PropertyShowService propertyShowService;

    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<GlobalResponse<PropertyShowResponse> >show(
            @PathVariable Long propertyId
    ) {
        PropertyShowResponse result = propertyShowService.show(propertyId);

        return ResponseEntity.status(200).body(
                GlobalResponse.<PropertyShowResponse>builder()
                        .code("00")
                        .message("매물 상세 정상 처리")
                        .data(result)
                        .build()
        );
    }

}
