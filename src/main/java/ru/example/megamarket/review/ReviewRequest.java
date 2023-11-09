package ru.example.megamarket.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    @JsonIgnore
    @NotEmpty
    private Integer sellerId;

    @Size(max = 200, message = "Слишком длинный отзыв")
    private String text;

    @NotEmpty
    @Min(value = 0, message = "Рейтинг может принимать значения от 0 до 5")
    @Max(value = 5, message = "Рейтинг может принимать значения от 0 до 5")
    private Integer rating;
}
