package ru.example.megamarket.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    @JsonIgnore
    private Integer sellerId;

    @Size(max = 200, message = "Слишком длинный отзыв")
    private String text;

    @NotNull
    @Min(value = 1, message = "Рейтинг может принимать значения от 1 до 5")
    @Max(value = 5, message = "Рейтинг может принимать значения от 0 до 5")
    private Integer rating;
}
