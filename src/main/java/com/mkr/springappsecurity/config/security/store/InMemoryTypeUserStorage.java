package com.mkr.springappsecurity.config.security.store;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

/*
InMemoryUserDetailsManager - всю информацию о пользователях будем хранить в памяти
*/
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(prefix = "app.security", name = "storage-type", havingValue = "in_memory")
public class InMemoryTypeUserStorage {

    @Bean
    public UserDetailsManager userDetailsManager() {
        // Предустановленные пользователи с ролями Admin и User
        UserDetails user1 = User.withUsername("user1")
            .password("{noop}user1Pass")
            .roles("USER")
            .build();

        UserDetails user2 = User.withUsername("admin")
            .password("{noop}adminPass")
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
