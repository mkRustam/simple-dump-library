package com.mkr.springappsecurity.genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAll();

    Optional<Genre> findByName(String name);

    Genre save(Genre genre);
}
