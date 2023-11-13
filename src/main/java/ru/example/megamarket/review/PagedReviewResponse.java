package ru.example.megamarket.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagedReviewResponse {
    private Integer totalPages;
    private List<ReviewResponse> reviewResponseList;
}
