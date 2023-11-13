package ru.example.megamarket.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public PagedReviewResponse getReviews(
            @PathVariable Integer sellerId,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Page<Review> page = service.getAllSellerReviews(sellerId, PageRequest.of(offset, limit));
        return PagedReviewResponse.builder()
                .totalPages(page.getTotalPages())
                .reviewResponseList(page.getContent().stream().map(mapper::reviewToReviewResponse).toList())
                .build();
    }

    @PostMapping("/{sellerId}")
    @Operation(description = "Создание отзыва о продавце")
    public ReviewResponse createReview(@RequestBody @Valid ReviewRequest request, @PathVariable Integer sellerId, Principal connectedUser) {
        request.setSellerId(sellerId);
        return mapper.reviewToReviewResponse(service.addReview(request, connectedUser));
    }

    @GetMapping
    @Operation(description = "Просмотр отзывов о текущем пользователе")
    public PagedReviewResponse getUserReviews(
            Principal connectedUser,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
                                               ) {
        Page<Review> page = service.getAllSellerReviews(connectedUser, PageRequest.of(offset, limit));
        return PagedReviewResponse.builder()
                .totalPages(page.getTotalPages())
                .reviewResponseList(page.getContent().stream().map(mapper::reviewToReviewResponse).toList())
                .build();
    }
}
