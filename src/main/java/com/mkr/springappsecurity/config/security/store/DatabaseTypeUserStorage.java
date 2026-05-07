package com.mkr.springappsecurity.config.security.store;

import com.mkr.springappsecurity.repository.UserRepository;
import com.mkr.springappsecurity.security.JpaUserDetailsManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

/*
InMemoryUserDetailsManager - всю информацию о пользователях будем хранить в памяти
*/
@Configuration
@EnableWebSecurity
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
