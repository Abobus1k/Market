package ru.example.megamarket.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "review", description = "Работа с отзывами")
public class ReviewController {
    private final ReviewService service;
    private final ReviewMapper mapper;

    @GetMapping("/{sellerId}")
    @Operation(description = "Просмотр отзывов о продавце")
    public List<ReviewResponse> getReviews(@PathVariable Integer sellerId) {
        return service.getAllSellerReviews(sellerId)
                .stream()
                .map(mapper::reviewToReviewResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/{sellerId}")
    @Operation(description = "Создание отзыва о продавце")
    public ReviewResponse createReview(@RequestBody @Valid ReviewRequest request, @PathVariable Integer sellerId, Principal connectedUser) {
        request.setSellerId(sellerId);
        return mapper.reviewToReviewResponse(service.addReview(request, connectedUser));
    }

    @GetMapping
    @Operation(description = "Просмотр отзывов о текущем пользователе")
    public List<ReviewResponse> getUserReviews(Principal connectedUser) {
        return service.getAllSellerReviews(connectedUser)
                .stream()
                .map(mapper::reviewToReviewResponse)
                .collect(Collectors.toList());
    }
}
