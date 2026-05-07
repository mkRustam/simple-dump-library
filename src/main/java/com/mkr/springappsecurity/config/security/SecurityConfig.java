package com.mkr.springappsecurity.config.security;

import com.mkr.springappsecurity.security.AuthManagerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
        HttpSecurity http
    ) throws Exception {
        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/person/**").authenticated()
                .requestMatchers("/library/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
                .loginPage("/login")
                // пользователь перенаправляется на URL, запрос становится GET, параметры в URL видны (например, ?error)
                .failureUrl("/login?error=true") // Не путать с failureForwardUrl
                    // Запрос остаётся POST, происходит внутреннее перенаправление на сервере без изменения URL в браузере;
                    // может привести к бесконечному циклу, если URL совпадает с URL обработки логина
//                .failureForwardUrl("...")
                .defaultSuccessUrl("/person", true)
                .permitAll()
            )
            .logout(logout -> logout
                // Используем дефолтную ссылку, либо можем указать кастомную и обрабатывать в контроллере
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true) // По умолчанию итак true
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(handling -> handling
                .authenticationEntryPoint((req, resp, e) -> {
                    resp
                        // Страница на которую будет редирект при попытке доступа к защищенным ресурсам
                        .sendRedirect("/login");
                })
            )
            // Установка всем конфигурациям значения по умолчанию
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
        UserDetailsManager userDetailsManager,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsManager);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
        HttpSecurity http,
        DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(daoAuthenticationProvider)
            .build();
    }

    @Bean
    @Scope("singleton")
    public AuthManagerUtil authManagerUtil() {
        return new AuthManagerUtil();
    }
}
