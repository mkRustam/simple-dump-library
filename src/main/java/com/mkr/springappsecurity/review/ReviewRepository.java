package com.mkr.springappsecurity.review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    List<Review> findAllByBookId(Long bookId);

    Optional<Review> findByBookIdAndAuthorId(Long bookId, Long personId);

    Optional<Review> findById(Long id);

    Review save(Review review);

    void deleteById(Long id);

    boolean existsByBookIdAndAuthorId(Long bookId, Long personId);
}
