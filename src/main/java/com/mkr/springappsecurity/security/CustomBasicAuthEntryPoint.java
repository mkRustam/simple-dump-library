package com.mkr.springappsecurity.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/*
BasicAuthenticationEntryPoint - этот компонент позволяет обрабатывать запросы,
требующие аутентификации, но не сопровождающиеся учётными данными пользователя,
и возвращать клиенту HTTP-ответ с сообщением об ошибке и заголовком,
указывающим на необходимость аутентификации.

Работа механизма
Процесс работы Spring BasicAuthenticationEntryPoint включает следующие этапы:
1) Неаутентифицированный запрос поступает в цепочку фильтров безопасности Spring Security.
    Выбрасывается исключение AccessDeniedException.
2) ExceptionTranslationFilter передаёт обработку исключения BasicAuthenticationEntryPoint.
3) BasicAuthenticationEntryPoint добавляет в ответ заголовок WWW-Authenticate: Basic realm="Realm name"
    и отправляет клиенту HTTP-статус 401 (Unauthorized).
4) Клиент получает этот ответ и знает, что необходимо повторно отправить запрос с заголовком Authorization,
содержащим логин и пароль, закодированные в Base64.
*/
@Component
public class CustomBasicAuthEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authEx)
        throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());

        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authEx.getMessage());
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("Your Realm Name");
        super.afterPropertiesSet();
    }
}