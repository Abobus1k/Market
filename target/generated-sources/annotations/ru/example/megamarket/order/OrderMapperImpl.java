package ru.example.megamarket.order;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.example.megamarket.listing.Listing;
import ru.example.megamarket.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-09T15:55:25+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponse orderToOrderResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setSellerId( orderSellerId( order ) );
        orderResponse.setBuyerId( orderBuyerId( order ) );
        orderResponse.setListingId( orderListingId( order ) );
        orderResponse.setId( order.getId() );
        orderResponse.setSum( order.getSum() );

        return orderResponse;
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
