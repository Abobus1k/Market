package ru.example.megamarket.listing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.megamarket.category.CategoryResponse;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingRequest {
    private String title;
    private String text;
    private Integer categoryId;
    private Integer price;
    private String city;
}
