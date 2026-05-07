package com.mkr.springappsecurity.book;

import com.mkr.springappsecurity.auth.AuthManagerUtil;
import com.mkr.springappsecurity.genre.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/library")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthManagerUtil authManagerUtil;
    private final GenreService genreService;

    @GetMapping
    public String library(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(required = false) Long genreId,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Page<BookDto> bookPage = bookService.searchAvailable(title, genreId, page);
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", bookPage.getNumber());
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("title", title);
        model.addAttribute("selectedGenreId", genreId);
        model.addAttribute("genres", genreService.findAll());
        return "private/library/books/list-page";
    }

    @GetMapping("/book/{id}")
    public String bookDetail(
        @PathVariable("id") Long bookId,
        @AuthenticationPrincipal UserDetails userDetails,
        Model model
    ) {
        BookDto book = bookService.findById(bookId);
        Long currentPersonId = authManagerUtil.getPersonId(userDetails);
        model.addAttribute("book", book);
        model.addAttribute("currentPersonId", currentPersonId);
        return "private/library/books/detail-page";
    }

    @PostMapping("/hold/{id}")
    public String hold(
        @PathVariable("id") Long bookId,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var personId = authManagerUtil.getPersonId(userDetails);
        bookService.attachBook(personId, bookId);
        return "redirect:/person";
    }
}
