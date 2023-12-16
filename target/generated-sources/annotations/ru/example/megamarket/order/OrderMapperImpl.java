package ru.example.megamarket.order;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.example.megamarket.listing.Listing;
import ru.example.megamarket.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-16T00:59:57+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponse orderToOrderResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();

        orderResponse.sellerId( orderSellerId( order ) );
        orderResponse.buyerId( orderBuyerId( order ) );
        orderResponse.listingId( orderListingId( order ) );
        orderResponse.id( order.getId() );
        orderResponse.sum( order.getSum() );
        orderResponse.status( order.getStatus() );

        return orderResponse.build();
    }

    private Integer orderSellerId(Order order) {
        if ( order == null ) {
            return null;
        }
        User seller = order.getSeller();
        if ( seller == null ) {
            return null;
        }
        Integer id = seller.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer orderBuyerId(Order order) {
        if ( order == null ) {
            return null;
        }
        User buyer = order.getBuyer();
        if ( buyer == null ) {
            return null;
        }
        Integer id = buyer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer orderListingId(Order order) {
        if ( order == null ) {
            return null;
        }
        Listing listing = order.getListing();
        if ( listing == null ) {
            return null;
        }
        Integer id = listing.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
