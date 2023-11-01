package ru.example.megamarket.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "sellerId", source = "order.seller.id")
    @Mapping(target = "buyerId", source = "order.buyer.id")
    @Mapping(target = "listingId", source = "order.listing.id")
    OrderResponse orderToOrderResponse(Order order);
}
