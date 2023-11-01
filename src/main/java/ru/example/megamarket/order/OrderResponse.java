package ru.example.megamarket.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer id;
    private Integer sellerId;
    private Integer buyerId;
    private Integer listingId;
    private Integer sum;
}
