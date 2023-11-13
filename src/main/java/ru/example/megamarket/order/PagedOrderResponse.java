package ru.example.megamarket.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedOrderResponse {
    private Integer totalPages;
    private List<OrderResponse> orderResponseList;
}
