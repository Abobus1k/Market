package ru.example.megamarket.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.megamarket.listing.ListingStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Integer id;
    private Integer sellerId;
    private Integer buyerId;
    private Integer sum;
    private OrderStatus status;
    private Integer listingId;
    private String listingTitle;
    private String listingText;
    private Integer listingCategoryId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime listingPostDate;
    private Boolean listingSold;
    private ListingStatus listingStatus;
}
