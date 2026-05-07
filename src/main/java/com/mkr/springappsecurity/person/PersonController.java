package com.mkr.springappsecurity.person;

import com.mkr.springappsecurity.auth.AuthManagerUtil;
import com.mkr.springappsecurity.book.BookDto;
import com.mkr.springappsecurity.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final BookService bookService;
    private final AuthManagerUtil authManagerUtil;

    @GetMapping
    public String person(
        Model model,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var personId = authManagerUtil.getPersonId(userDetails);
        List<BookDto> books = personService.findBooksForPerson(personId);
        model.addAttribute("books", books);
        return "private/person-page";
    }

    @PostMapping("/unhold/{id}")
    public String unhold(@PathVariable("id") Long bookId) {
        bookService.deattachBook(bookId);
        return "redirect:/person";
    }
}
