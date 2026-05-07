package com.mkr.springappsecurity.repository.jpa;

import com.mkr.springappsecurity.entity.model.book.Book;
import com.mkr.springappsecurity.repository.BookRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookRepository extends BookRepository, JpaRepository<Book, Long> {
}
