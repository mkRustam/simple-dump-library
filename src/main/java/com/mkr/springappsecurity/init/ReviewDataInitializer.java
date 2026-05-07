package com.mkr.springappsecurity.init;

import com.mkr.springappsecurity.book.Book;
import com.mkr.springappsecurity.book.BookRepository;
import com.mkr.springappsecurity.person.Person;
import com.mkr.springappsecurity.person.PersonRepository;
import com.mkr.springappsecurity.review.Review;
import com.mkr.springappsecurity.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class ReviewDataInitializer implements CommandLineRunner {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Override
    public void run(String... args) {
        log.info("Initializing review data...");

        Optional<Book> warAndPeace = bookRepository.findByTitle("War and Peace");
        Optional<Book> nineteenEightyFour = bookRepository.findByTitle("1984");
        Optional<Book> gatsby = bookRepository.findByTitle("The Great Gatsby");

        // person IDs correspond to seeded users: user1 -> Person "User One", admin1 -> Person "Admin One"
        // We look them up by iterating known IDs (1 and 2 from auto-increment)
        Optional<Person> person1 = personRepository.findById(1L);
        Optional<Person> person2 = personRepository.findById(2L);

        if (person1.isEmpty() || person2.isEmpty()) {
            log.info("Initializing review data...Persons not found, skipping");
            return;
        }

        Person user = person1.get();
        Person admin = person2.get();

        warAndPeace.ifPresent(book -> createIfAbsent(book, user, 5, "A masterpiece of world literature. Tolstoy's epic is unmatched."));
        warAndPeace.ifPresent(book -> createIfAbsent(book, admin, 4, "Long but rewarding. The battle scenes are incredible."));
        nineteenEightyFour.ifPresent(book -> createIfAbsent(book, user, 5, "Terrifyingly relevant. Everyone should read this."));
        gatsby.ifPresent(book -> createIfAbsent(book, admin, 3, "Beautiful prose but the characters are hard to sympathize with."));
        gatsby.ifPresent(book -> createIfAbsent(book, user, 4, "A vivid portrait of the Jazz Age. Fitzgerald at his best."));

        log.info("Initializing review data...Done");
    }

    private void createIfAbsent(Book book, Person author, int rating, String text) {
        if (reviewRepository.existsByBookIdAndAuthorId(book.getId(), author.getId())) {
            return;
        }
        Review review = new Review();
        review.setBook(book);
        review.setAuthor(author);
        review.setRating(rating);
        review.setText(text);
        reviewRepository.save(review);
    }
}
