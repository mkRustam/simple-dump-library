package com.mkr.springappsecurity.person;

import com.mkr.springappsecurity.book.BookDto;
import com.mkr.springappsecurity.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;
    private final BookService bookService;

    public List<BookDto> findBooksForPerson(Long personId) {
        return bookService.findByHolder(personId);
    }
}
