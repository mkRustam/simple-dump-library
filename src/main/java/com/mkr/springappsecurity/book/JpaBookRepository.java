package com.mkr.springappsecurity.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookRepository extends BookRepository, JpaRepository<Book, Long> {
}
