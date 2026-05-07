package com.mkr.springappsecurity.book;

import com.mkr.springappsecurity.auth.AuthManagerUtil;
import com.mkr.springappsecurity.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthManagerUtil authManagerUtil;

    @GetMapping
    public String library(Model model) {
        var books = bookService.findAllAvailable();
        model.addAttribute("books", books);
        return "private/library/books/list-page";
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
