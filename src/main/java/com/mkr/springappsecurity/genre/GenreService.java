package com.mkr.springappsecurity.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<GenreDto> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(GenreDto::toDto)
                .toList();
    }
}
