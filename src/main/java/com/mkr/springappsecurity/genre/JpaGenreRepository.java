package com.mkr.springappsecurity.genre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGenreRepository extends GenreRepository, JpaRepository<Genre, Long> {
}
