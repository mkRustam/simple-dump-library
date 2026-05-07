package com.mkr.springappsecurity.review;

import com.mkr.springappsecurity.book.Book;
import com.mkr.springappsecurity.book.BookRepository;
import com.mkr.springappsecurity.person.Person;
import com.mkr.springappsecurity.person.PersonRepository;
import com.mkr.springappsecurity.review.exception.ReviewAlreadyExistsException;
import com.mkr.springappsecurity.review.exception.ReviewNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Book book;
    private Person person;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        person = new Person();
        person.setId(1L);
        person.setName("Test User");
    }

    @Test
    void createReview_success() {
        CreateReviewRequest request = new CreateReviewRequest("Great book!", 5);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(reviewRepository.existsByBookIdAndAuthorId(1L, 1L)).thenReturn(false);

        Review savedReview = new Review();
        savedReview.setId(1L);
        savedReview.setText("Great book!");
        savedReview.setRating(5);
        savedReview.setBook(book);
        savedReview.setAuthor(person);
        savedReview.setCreatedAt(LocalDateTime.now());
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        ReviewDto result = reviewService.createReview(1L, 1L, request);

        assertThat(result.getText()).isEqualTo("Great book!");
        assertThat(result.getRating()).isEqualTo(5);
        assertThat(result.getAuthorName()).isEqualTo("Test User");
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void createReview_bookNotFound_throwsException() {
        CreateReviewRequest request = new CreateReviewRequest("Great!", 5);
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.createReview(99L, 1L, request))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessageContaining("Book with id 99 not found");
    }

    @Test
    void createReview_duplicate_throwsException() {
        CreateReviewRequest request = new CreateReviewRequest("Great!", 5);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(reviewRepository.existsByBookIdAndAuthorId(1L, 1L)).thenReturn(true);

        assertThatThrownBy(() -> reviewService.createReview(1L, 1L, request))
            .isInstanceOf(ReviewAlreadyExistsException.class)
            .hasMessageContaining("already reviewed");
    }

    @Test
    void getReviewsForBook_returnsList() {
        Review review = new Review();
        review.setId(1L);
        review.setText("Nice");
        review.setRating(4);
        review.setBook(book);
        review.setAuthor(person);
        review.setCreatedAt(LocalDateTime.now());

        when(reviewRepository.findAllByBookId(1L)).thenReturn(List.of(review));

        List<ReviewDto> result = reviewService.getReviewsForBook(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRating()).isEqualTo(4);
    }

    @Test
    void deleteReview_notFound_throwsException() {
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.deleteReview(99L))
            .isInstanceOf(ReviewNotFoundException.class);
    }

    @Test
    void deleteOwnReview_success() {
        Review review = new Review();
        review.setId(1L);
        review.setAuthor(person);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        reviewService.deleteOwnReview(1L, 1L);

        verify(reviewRepository).deleteById(1L);
    }

    @Test
    void deleteOwnReview_notOwner_throwsAccessDenied() {
        Person otherPerson = new Person();
        otherPerson.setId(2L);

        Review review = new Review();
        review.setId(1L);
        review.setAuthor(otherPerson);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        assertThatThrownBy(() -> reviewService.deleteOwnReview(1L, 1L))
            .isInstanceOf(AccessDeniedException.class)
            .hasMessageContaining("own reviews");
    }
}
