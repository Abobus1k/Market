package ru.example.megamarket.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotEmpty(message = "Не заполнено имя категории")
    @Size(min = 3, max = 20, message = "Недопустимое название категории")
    private String name;
}
