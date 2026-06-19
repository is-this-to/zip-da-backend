package com.zipdabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ZipDaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipDaBackendApplication.class, args);
    }

}
