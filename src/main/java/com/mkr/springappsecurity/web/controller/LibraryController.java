package com.mkr.springappsecurity.web.controller;

import com.mkr.springappsecurity.entity.model.security.User;
import com.mkr.springappsecurity.security.AuthManagerUtil;
import com.mkr.springappsecurity.service.BookService;
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
public class LibraryController {

    private final BookService bookService;
    private final AuthManagerUtil authManagerUtil;

    @GetMapping
    public String library(
        Model model
    ) {
        var books = bookService.findAllAvailable();
        model.addAttribute("books", books);
        return "private/library/books/list-page";
    }

    @PostMapping("/hold/{id}")
    public String hold(
        @PathVariable("id") Long bookId,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var personId = ((User) userDetails).getPerson().getId();
        bookService.attachBook(personId, bookId);
        return "redirect:/person";
    }
}
