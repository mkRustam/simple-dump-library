package com.mkr.springappsecurity.init;

import com.mkr.springappsecurity.book.Book;
import com.mkr.springappsecurity.book.BookRepository;
import com.mkr.springappsecurity.genre.Genre;
import com.mkr.springappsecurity.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
@Order(3)
@RequiredArgsConstructor
public class GenreDataInitializer implements CommandLineRunner {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initializing genre data...");

        Genre classic = findOrCreate("Classic");
        Genre fiction = findOrCreate("Fiction");
        Genre dystopia = findOrCreate("Dystopia");
        Genre romance = findOrCreate("Romance");
        Genre adventure = findOrCreate("Adventure");

        Map<String, Set<Genre>> bookGenres = Map.ofEntries(
            Map.entry("War and Peace", Set.of(classic, fiction)),
            Map.entry("Silent Done", Set.of(classic, fiction)),
            Map.entry("Captain's Daughter", Set.of(classic, adventure)),
            Map.entry("To Kill a Mockingbird", Set.of(classic, fiction)),
            Map.entry("1984", Set.of(classic, dystopia)),
            Map.entry("Pride and Prejudice", Set.of(classic, romance)),
            Map.entry("The Great Gatsby", Set.of(classic, fiction)),
            Map.entry("Moby-Dick", Set.of(classic, adventure)),
            Map.entry("The Catcher in the Rye", Set.of(classic, fiction)),
            Map.entry("Brave New World", Set.of(classic, dystopia)),
            Map.entry("Jane Eyre", Set.of(classic, romance)),
            Map.entry("The Lord of the Rings", Set.of(fiction, adventure)),
            Map.entry("Animal Farm", Set.of(classic, dystopia))
        );

        for (Map.Entry<String, Set<Genre>> entry : bookGenres.entrySet()) {
            Optional<Book> bookOpt = bookRepository.findByTitle(entry.getKey());
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                if (book.getGenres().isEmpty()) {
                    book.getGenres().addAll(entry.getValue());
                    bookRepository.save(book);
                }
            }
        }

        log.info("Initializing genre data...Done");
    }

    private Genre findOrCreate(String name) {
        Optional<Genre> existing = genreRepository.findByName(name);
        if (existing.isPresent()) {
            return existing.get();
        }
        Genre genre = new Genre();
        genre.setName(name);
        return genreRepository.save(genre);
    }
}
