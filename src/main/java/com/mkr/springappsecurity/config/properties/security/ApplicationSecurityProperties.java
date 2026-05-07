package com.mkr.springappsecurity.config.properties.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.security")
public class ApplicationSecurityProperties {

    private StorageType storageType;

    public enum StorageType {
        IN_MEMORY,
        DATABASE
    }
}
