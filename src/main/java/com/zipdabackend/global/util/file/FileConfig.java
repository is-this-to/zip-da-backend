package com.zipdabackend.global.util.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "file")
public record FileConfig(
        String serverUri
        , String storagePath
        , String profilePath
        , String propertyPath
        , List<String> allowExtensionList
) {
}
