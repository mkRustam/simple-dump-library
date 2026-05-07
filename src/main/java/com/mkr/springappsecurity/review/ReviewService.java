package com.mkr.springappsecurity.review;

import com.mkr.springappsecurity.book.Book;
import com.mkr.springappsecurity.book.BookRepository;
import com.mkr.springappsecurity.person.Person;
import com.mkr.springappsecurity.person.PersonRepository;
import com.mkr.springappsecurity.review.exception.ReviewAlreadyExistsException;
import com.mkr.springappsecurity.review.exception.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Transactional
    public ReviewDto createReview(Long bookId, Long personId, CreateReviewRequest request) {
        log.info("Creating review for book {} by person {}", bookId, personId);

        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new NoSuchElementException("Book with id " + bookId + " not found"));

        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new NoSuchElementException("Person with id " + personId + " not found"));

        if (reviewRepository.existsByBookIdAndAuthorId(bookId, personId)) {
            throw new ReviewAlreadyExistsException("You have already reviewed this book");
        }

        Review review = new Review();
        review.setText(request.getText());
        review.setRating(request.getRating());
        review.setBook(book);
        review.setAuthor(person);

        Review saved = reviewRepository.save(review);
        log.info("Review created with id {}", saved.getId());
        return ReviewDto.toDto(saved);
    }

    public List<ReviewDto> getReviewsForBook(Long bookId) {
        log.info("Getting reviews for book {}", bookId);
        return reviewRepository.findAllByBookId(bookId)
            .stream()
            .map(ReviewDto::toDto)
            .toList();
    }

    public Double getAverageRating(Long bookId) {
        if (reviewRepository instanceof JpaReviewRepository jpaRepo) {
            return jpaRepo.findAverageRatingByBookId(bookId);
        }
        List<Review> reviews = reviewRepository.findAllByBookId(bookId);
        if (reviews.isEmpty()) return null;
        return reviews.stream()
            .mapToInt(Review::getRating)
            .average()
            .orElse(0.0);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        log.info("Deleting review {} (moderation)", reviewId);
        if (reviewRepository.findById(reviewId).isEmpty()) {
            throw new ReviewNotFoundException("Review with id " + reviewId + " not found");
        }
        reviewRepository.deleteById(reviewId);
    }

    @Transactional
    public void deleteOwnReview(Long reviewId, Long personId) {
        log.info("Person {} deleting own review {}", personId, reviewId);
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException("Review with id " + reviewId + " not found"));

        if (!review.getAuthor().getId().equals(personId)) {
            throw new AccessDeniedException("You can only delete your own reviews");
        }
        reviewRepository.deleteById(reviewId);
    }
}
