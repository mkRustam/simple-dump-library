package com.mkr.springappsecurity.init;

import com.mkr.springappsecurity.book.Book;
import com.mkr.springappsecurity.book.BookService;
import com.mkr.springappsecurity.book.exception.BookAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(2)
@RequiredArgsConstructor
public class LibraryDataInitializer implements CommandLineRunner {

    private final BookService bookService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing library data...");
        try {
            addBooks();
            log.info("Initializing library data...Done");
        } catch (BookAlreadyExistsException ex) {
            log.info("Initializing library data...Already exists");
        }
    }

    private void addBooks() throws BookAlreadyExistsException {
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
