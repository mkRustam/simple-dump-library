package com.mkr.springappsecurity.review;

import com.mkr.springappsecurity.auth.AuthManagerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/library/book/{bookId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthManagerUtil authManagerUtil;

    @GetMapping
    @PreAuthorize("hasAuthority('BOOK_READ')")
    public String reviews(
        @PathVariable("bookId") Long bookId,
        @AuthenticationPrincipal UserDetails userDetails,
        Model model
    ) {
        List<ReviewDto> reviews = reviewService.getReviewsForBook(bookId);
        Double averageRating = reviewService.getAverageRating(bookId);
        Long currentPersonId = authManagerUtil.getPersonId(userDetails);

        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("bookId", bookId);
        model.addAttribute("currentPersonId", currentPersonId);
        return "private/library/books/reviews-page";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('REVIEW_CREATE')")
    public String createReview(
        @PathVariable("bookId") Long bookId,
        @AuthenticationPrincipal UserDetails userDetails,
        @ModelAttribute CreateReviewRequest request
    ) {
        Long personId = authManagerUtil.getPersonId(userDetails);
        reviewService.createReview(bookId, personId, request);
        return "redirect:/library/book/" + bookId + "/reviews";
    }

    @PostMapping("/{reviewId}/delete")
    @PreAuthorize("hasAuthority('REVIEW_CREATE') or hasAuthority('REVIEW_MANAGE')")
    public String deleteReview(
        @PathVariable("bookId") Long bookId,
        @PathVariable("reviewId") Long reviewId,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long personId = authManagerUtil.getPersonId(userDetails);
        boolean hasManageAuthority = userDetails.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("REVIEW_MANAGE"));

        if (hasManageAuthority) {
            reviewService.deleteReview(reviewId);
        } else {
            reviewService.deleteOwnReview(reviewId, personId);
        }
        return "redirect:/library/book/" + bookId + "/reviews";
    }
}
