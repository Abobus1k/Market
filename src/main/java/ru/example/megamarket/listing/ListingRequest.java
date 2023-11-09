package ru.example.megamarket.listing;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.megamarket.category.CategoryResponse;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingRequest {
    @NotEmpty(message = "Название товара недолжно быть пустым")
    @Size(min = 3, max = 20, message = "Недопустимое название товара")
    private String title;

    @Max(value = 400, message = "Недопустимое описание товара")
    private String text;

    @NotEmpty(message = "У товара должна быть категория")
    private Integer categoryId;

    @NotEmpty(message = "У товара должна быть цена")
    @Min(value = 1, message = "Недопустимая цена")
    private Integer price;
}
