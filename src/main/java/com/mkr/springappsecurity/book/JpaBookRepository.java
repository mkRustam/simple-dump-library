package com.mkr.springappsecurity.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaBookRepository extends BookRepository, JpaRepository<Book, Long> {

    @Query(
        value = "SELECT DISTINCT b FROM Book b LEFT JOIN b.genres g " +
                "WHERE b.holder IS NULL " +
                "AND (:title = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
                "AND (:genreId IS NULL OR g.id = :genreId)",
        countQuery = "SELECT COUNT(DISTINCT b) FROM Book b LEFT JOIN b.genres g " +
                     "WHERE b.holder IS NULL " +
                     "AND (:title = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
                     "AND (:genreId IS NULL OR g.id = :genreId)"
    )
    Page<Book> findAvailableByFilter(@Param("title") String title,
                                     @Param("genreId") Long genreId,
                                     Pageable pageable);
}
