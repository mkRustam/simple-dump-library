package com.mkr.springappsecurity.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private StorageType storageType;

    public enum StorageType {
        IN_MEMORY,
        DATABASE
    }
}
