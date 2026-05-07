package com.mkr.springappsecurity.book;

import com.mkr.springappsecurity.book.exception.BookAlreadyExistsException;
import com.mkr.springappsecurity.person.Person;
import com.mkr.springappsecurity.person.PersonRepository;
import com.mkr.springappsecurity.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;
    private final ReviewService reviewService;

    public void addBook(Book book) throws BookAlreadyExistsException {
        log.info("Checking book {}", book);
        var bookOptional = bookRepository.findByTitle(book.getTitle());
        if (bookOptional.isPresent()) {
            throw new BookAlreadyExistsException();
        }
        log.info("Adding book");
        bookRepository.save(book);
    }

    public List<BookDto> findAllAvailable() {
        log.info("Find all Books");
        return bookRepository
            .findAllByHolderIsNull()
            .stream()
            .map(book -> {
                BookDto dto = BookDto.toDto(book);
                dto.setAverageRating(reviewService.getAverageRating(book.getId()));
                return dto;
            })
            .toList();
    }

    private static final int PAGE_SIZE = 9;

    @Transactional(readOnly = true)
    public Page<BookDto> searchAvailable(String title, Long genreId, int page) {
        log.info("Search available books: title='{}', genreId={}, page={}", title, genreId, page);
        PageRequest pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("title").ascending());
        Page<Book> bookPage = bookRepository.findAvailableByFilter(
                title == null ? "" : title, genreId, pageable);
        return bookPage.map(book -> {
            BookDto dto = BookDto.toDto(book);
            dto.setAverageRating(reviewService.getAverageRating(book.getId()));
            return dto;
        });
    }

    public BookDto findById(Long id) {
        log.info("Find Book by id {}", id);
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Book with id " + id + " not found"));
        BookDto dto = BookDto.toDto(book);
        dto.setAverageRating(reviewService.getAverageRating(id));
        if (book.getHolder() != null) {
            dto.setHolderId(book.getHolder().getId());
            dto.setHolderName(book.getHolder().getName());
        }
        return dto;
    }

    public List<BookDto> findByHolder(Long holderId) {
        log.info("Find Books by holder {}", holderId);
        return bookRepository
            .findAllByHolderId(holderId)
            .stream()
            .map(BookDto::toDto)
            .toList();
    }

    @Transactional
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

    @Transactional
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
