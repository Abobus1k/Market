package ru.example.megamarket.review;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/reviews")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "admin_review", description = "Работа с отзывами для админа")
public class AdminReviewController {
    private final ReviewService service;
    private final ReviewMapper mapper;

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> remove(@PathVariable Integer reviewId) {
        service.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
