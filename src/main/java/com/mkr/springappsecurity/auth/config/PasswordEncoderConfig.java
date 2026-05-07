package com.mkr.springappsecurity.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    @Primary
    @Scope(value = "singleton")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
