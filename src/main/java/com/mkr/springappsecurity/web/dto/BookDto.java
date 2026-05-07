package com.mkr.springappsecurity.web.dto;

import com.mkr.springappsecurity.entity.model.book.Book;
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
