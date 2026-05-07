package com.mkr.springappsecurity.service;

import com.mkr.springappsecurity.entity.model.book.Book;
import com.mkr.springappsecurity.entity.model.Person;
import com.mkr.springappsecurity.entity.model.book.error.BookAlreadyExists;
import com.mkr.springappsecurity.repository.BookRepository;
import com.mkr.springappsecurity.repository.PersonRepository;
import com.mkr.springappsecurity.web.dto.BookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    public void addBook(Book book) throws BookAlreadyExists {
        log.info("Checking book {}", book);
        var bookOptional = bookRepository.findByTitle(book.getTitle());
        if (bookOptional.isPresent()) {
            throw new BookAlreadyExists();
        }
        log.info("Adding book");
        bookRepository.save(book);
    }

    public List<BookDto> findAllAvailable() {
        log.info("Find all Books");
        return bookRepository
            .findAllByHolderIsNull()
            .stream()
            .map(BookDto::toDto)
            .toList();
    }

    public List<BookDto> findByHolder(Long holderId) {
        log.info("Find Books by holder {}", holderId);
        return bookRepository
            .findAllByHolderId(holderId)
            .stream()
            .map(BookDto::toDto)
            .toList();
    }

    public void attachBook(Long personId, Long bookId) {
        log.info("Person with id {} taking book with id {}", personId, bookId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            var book = bookOptional.get();
            Optional<Person> personOptional = personRepository.findById(personId);
            if (personOptional.isPresent()) {
                var person = personOptional.get();
                book.setHolder(person);
                bookRepository.save(book);
            } else {
                throw new NoSuchElementException("Person with id " + personId + " not found");
            }
        } else {
            throw new NoSuchElementException("Book with id " + bookId + " not found");
        }
    }

    public void deattachBook(Long bookId) {
        log.info("Person returning book with id {}", bookId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            var book = bookOptional.get();
            book.setHolder(null);
            bookRepository.save(book);
        } else {
            throw new NoSuchElementException("Book with id " + bookId + " not found");
        }
    }

}
