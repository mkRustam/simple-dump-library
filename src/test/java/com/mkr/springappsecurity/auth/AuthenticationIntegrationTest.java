package com.mkr.springappsecurity.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void authenticatedUser_canAccessPersonPage() throws Exception {
        mockMvc.perform(get("/person")
                .header("Authorization", basicAuth("user1", "password1")))
            .andExpect(status().isOk());
    }

    @Test
    void unauthenticatedUser_isRedirectedToLogin() throws Exception {
        mockMvc.perform(get("/person"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    void invalidCredentials_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/person")
                .header("Authorization", basicAuth("user1", "wrongpassword")))
            .andExpect(status().isUnauthorized());
    }

    private String basicAuth(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
