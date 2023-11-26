package ru.example.megamarket.listing;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.megamarket.category.CategoryResponse;
import ru.example.megamarket.user.UserStatus;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postDate;
    private Boolean sold;
    private Integer userId;
    private ListingStatus listingStatus;
}
