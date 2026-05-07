package com.mkr.springappsecurity.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

    private Long id;

    private String title;

    private String author;

    private String description;

    private Double averageRating;

    private Long holderId;

    private String holderName;

    private List<String> genreNames;

    public static BookDto toDto(Book book) {
        List<String> genres = book.getGenres() == null
                ? Collections.emptyList()
                : book.getGenres().stream()
                    .map(g -> g.getName())
                    .sorted()
                    .toList();
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setGenreNames(genres);
        return dto;
    }
}
