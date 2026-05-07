package com.mkr.springappsecurity.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findByTitle(String title);

    Optional<Book> findById(Long id);

    Book save(Book entity);

    List<Book> findAllByHolderId(Long holderId);

    List<Book> findAllByHolderIsNull();

    Page<Book> findAvailableByFilter(String title, Long genreId, Pageable pageable);
}
