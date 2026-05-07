package com.mkr.springappsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootApplication
public class SpringAppSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAppSecurityApplication.class, args);
        sendRequest("user1", "user1");
    }

    private static void sendRequest(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:8080/person",
            HttpMethod.GET,
            request,
            String.class
        );

        var statusCode = response.getStatusCode();
    }
}
