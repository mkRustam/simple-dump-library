package com.mkr.springappsecurity.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

    private Long id;

    private String title;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle());
    }
}
