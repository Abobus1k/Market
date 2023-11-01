package ru.example.megamarket.listing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.megamarket.category.CategoryResponse;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingResponse {
    private Integer id;
    private String title;
    private String text;
    private String categoryId;
    private Integer price;
    private String city;
    private LocalDateTime postDate;
    private Boolean sold;
    private Integer userId;
}
