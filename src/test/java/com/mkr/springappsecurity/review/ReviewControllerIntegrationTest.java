package com.mkr.springappsecurity.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getReviews_authenticatedUser_returnsPage() throws Exception {
        mockMvc.perform(get("/library/book/1/reviews")
                .header("Authorization", basicAuth("user1", "password1")))
            .andExpect(status().isOk())
            .andExpect(view().name("private/library/books/reviews-page"))
            .andExpect(model().attributeExists("reviews"))
            .andExpect(model().attributeExists("bookId"));
    }

    @Test
    void getReviews_unauthenticated_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/library/book/1/reviews"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    void createReview_withReviewCreateAuthority_succeeds() throws Exception {
        mockMvc.perform(post("/library/book/2/reviews")
                .header("Authorization", basicAuth("user1", "password1"))
                .param("text", "Excellent read!")
                .param("rating", "5"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/library/book/2/reviews"));
    }

    @Test
    void createReview_duplicate_showsError() throws Exception {
        // First review
        mockMvc.perform(post("/library/book/3/reviews")
                .header("Authorization", basicAuth("user1", "password1"))
                .param("text", "First review")
                .param("rating", "4"))
            .andExpect(status().is3xxRedirection());

        // Duplicate attempt
        mockMvc.perform(post("/library/book/3/reviews")
                .header("Authorization", basicAuth("user1", "password1"))
                .param("text", "Second review attempt")
                .param("rating", "3"))
            .andExpect(status().is3xxRedirection());
    }

    private String basicAuth(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
