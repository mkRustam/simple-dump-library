package com.mkr.springappsecurity.book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findByTitle(String title);

    Optional<Book> findById(Long id);

    Book save(Book entity);

    List<Book> findAllByHolderId(Long holderId);

    List<Book> findAllByHolderIsNull();
}
