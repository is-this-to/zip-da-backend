package com.zipdabackend.domain.file.controller;


import com.zipdabackend.domain.file.response.FileResponse;
import com.zipdabackend.domain.file.service.FileService;
import com.zipdabackend.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class FileController {


    private final FileService fileService;

    @PostMapping("/agent-images")
    public ResponseEntity<GlobalResponse<FileResponse>> storeProfile(
            @ModelAttribute MultipartFile file
    ){
        return ResponseEntity.status(200).body(
                GlobalResponse.<FileResponse>builder()
                        .code("00")
                        .message("파일 저장 성공")
                        .data(fileService.storeProfile(file))
                        .build()
        );
    }

    @PostMapping("/property-images")
    public ResponseEntity<GlobalResponse<FileResponse>> storePropertyImage(
            @ModelAttribute MultipartFile file
    ){
        return ResponseEntity.status(200).body(
            GlobalResponse.<FileResponse>builder()
                    .code("00")
                    .message("매물 이미지 저장 성공")
                    .data(fileService.storePropertyImage(file))
                    .build()
        );
    }

    // 2. 매물 이미지 단일 삭제 API (최종 등록 전 컴포넌트 내부에서 취소 시 호출)
    @DeleteMapping("/property-images")
    public ResponseEntity<GlobalResponse<Void>> deletePropertyImage(
        @RequestParam("fileUri") String fileUri
    ){
        fileService.removePropertyImage(fileUri);
        return ResponseEntity.status(200).body(
                GlobalResponse.<Void>builder()
                        .code("00")
                        .message("매물 이미지 삭제 성공")
                        .build()
        );
    }

}
