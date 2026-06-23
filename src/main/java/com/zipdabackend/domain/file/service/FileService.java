package com.zipdabackend.domain.file.service;


import com.zipdabackend.domain.file.response.FileResponse;
import com.zipdabackend.global.util.file.FileConfig;
import com.zipdabackend.global.util.file.LocalFileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {


    private final LocalFileManager localFileManager;
    private final FileConfig fileConfig;

    public FileResponse storeProfile(MultipartFile file){
        // 파일 경로 생성
        String path = localFileManager.generateProfilePath(file);

        // 파일 저장
        localFileManager.saveFile(file, path);

        return FileResponse.builder()
                .fileUri(fileConfig.serverUri() + path)
                .build();
    }

    // 매물 이미지 단일 업로드 처리
    public FileResponse storePropertyImage(MultipartFile file){
        String path = localFileManager.generatePropertyPath(file);
        localFileManager.saveFile(file, path);

        return FileResponse.builder()
                .fileUri(fileConfig.serverUri() + path)
                .build();
    }

    // 매물 이미지 단일 삭제 처리(전체 URL에서 도메인부를 가공하여 삭제 요청)
    public void removePropertyImage(String fileUri){
        String logicalPath = fileUri.replace(fileConfig.serverUri(), "");
        localFileManager.deleteFile(logicalPath);
    }
}
