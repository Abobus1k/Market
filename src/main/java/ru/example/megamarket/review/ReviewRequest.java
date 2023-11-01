package ru.example.megamarket.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    @JsonIgnore
    private Integer sellerId;

    private String text;

    private Integer rating;
}
