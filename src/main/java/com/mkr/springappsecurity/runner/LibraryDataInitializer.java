package com.mkr.springappsecurity.runner;

import com.mkr.springappsecurity.entity.model.book.Book;
import com.mkr.springappsecurity.entity.model.book.error.BookAlreadyExists;
import com.mkr.springappsecurity.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LibraryDataInitializer implements CommandLineRunner {

    private final BookService bookService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing library data...");
        try {
            addBooks();
            log.info("Initializing library data...Done");
        } catch (BookAlreadyExists ex) {
            log.info("Initializing library data...Already exists");
        }
    }

    private void addBooks() throws BookAlreadyExists {
        bookService.addBook(createBook("War and Peace"));
        bookService.addBook(createBook("Silent Done"));
        bookService.addBook(createBook("Captain's Daughter"));
        bookService.addBook(createBook("To Kill a Mockingbird"));
        bookService.addBook(createBook("1984"));
        bookService.addBook(createBook("Pride and Prejudice"));
        bookService.addBook(createBook("The Great Gatsby"));
        bookService.addBook(createBook("Moby-Dick"));
        bookService.addBook(createBook("The Catcher in the Rye"));
        bookService.addBook(createBook("Brave New World"));
        bookService.addBook(createBook("Jane Eyre"));
        bookService.addBook(createBook("The Lord of the Rings"));
        bookService.addBook(createBook("Animal Farm"));
    }

    private Book createBook(String title) {
        Book book = new Book();
        book.setTitle(title);
        return book;
    }
}
