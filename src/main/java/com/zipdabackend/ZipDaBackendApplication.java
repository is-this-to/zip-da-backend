package com.zipdabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("com.zipdabackend.domain.bookmark.mapper")
public class ZipDaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipDaBackendApplication.class, args);
    }

}
