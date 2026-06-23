package com.zipdabackend.domain.file.response;

import lombok.Builder;

@Builder
public record FileResponse(
        String fileUri
) {
}
