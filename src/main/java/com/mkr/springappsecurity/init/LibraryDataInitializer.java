package com.mkr.springappsecurity.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkr.springappsecurity.book.Book;
import com.mkr.springappsecurity.book.BookRepository;
import com.mkr.springappsecurity.genre.Genre;
import com.mkr.springappsecurity.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

@Component
@Slf4j
@Order(2)
@RequiredArgsConstructor
public class LibraryDataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Initializing library data from books.json...");

        InputStream is = new ClassPathResource("data/books.json").getInputStream();
        List<BookEntry> entries = objectMapper.readValue(is, new TypeReference<>() {});

        Map<String, Genre> genreCache = new HashMap<>();
        int created = 0;

        for (BookEntry entry : entries) {
            if (bookRepository.findByTitle(entry.title()).isPresent()) {
                continue;
            }

            Set<Genre> genres = new HashSet<>();
            for (String genreName : entry.genres()) {
                genres.add(genreCache.computeIfAbsent(genreName, this::findOrCreateGenre));
            }

            Book book = new Book();
            book.setTitle(entry.title());
            book.setAuthor(entry.author());
            book.setDescription(entry.description());
            book.setGenres(genres);
            bookRepository.save(book);
            created++;
        }

        log.info("Initializing library data...Done ({} books created, {} genres)", created, genreCache.size());
    }

    private Genre findOrCreateGenre(String name) {
        return genreRepository.findByName(name)
                .orElseGet(() -> {
                    Genre genre = new Genre();
                    genre.setName(name);
                    return genreRepository.save(genre);
                });
    }

    private record BookEntry(String author, String title, List<String> genres, String description) {}
}
