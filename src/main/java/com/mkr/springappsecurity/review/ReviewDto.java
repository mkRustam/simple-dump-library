package com.mkr.springappsecurity.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long id;
    private String text;
    private Integer rating;
    private String authorName;
    private Long authorId;
    private LocalDateTime createdAt;

    public static ReviewDto toDto(Review review) {
        return new ReviewDto(
            review.getId(),
            review.getText(),
            review.getRating(),
            review.getAuthor().getName(),
            review.getAuthor().getId(),
            review.getCreatedAt()
        );
    }
}
