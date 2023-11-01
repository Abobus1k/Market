package ru.example.megamarket.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;
    private final ReviewMapper mapper;

    @GetMapping("/{sellerId}")
    public List<ReviewResponse> getReviews(@PathVariable Integer sellerId) {
        return service.getAllSellerReviews(sellerId)
                .stream()
                .map(mapper::reviewToReviewResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/{sellerId}")
    public ResponseEntity<Void> createReview(ReviewRequest request, @PathVariable Integer sellerId, Principal connectedUser) {
        request.setSellerId(sellerId);
        service.addReview(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<ReviewResponse> getUserReviews(Principal connectedUser) {
        return service.getAllSellerReviews(connectedUser)
                .stream()
                .map(mapper::reviewToReviewResponse)
                .collect(Collectors.toList());
    }
}
