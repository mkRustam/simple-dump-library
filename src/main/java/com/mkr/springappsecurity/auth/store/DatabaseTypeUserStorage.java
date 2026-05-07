package com.mkr.springappsecurity.auth.store;

import com.mkr.springappsecurity.auth.JpaUserDetailsManager;
import com.mkr.springappsecurity.auth.repository.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@ConditionalOnProperty(prefix = "app.security", name = "storage-type", havingValue = "database")
public class DatabaseTypeUserStorage {

    @Bean
    public UserDetailsManager userDetailsManager(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        return new JpaUserDetailsManager(userRepository, passwordEncoder);
    }
}
