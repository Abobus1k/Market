package ru.example.megamarket.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Integer id;
    private Integer sellerId;
    private Integer buyerId;
    private String text;
    private Integer rating;
}
