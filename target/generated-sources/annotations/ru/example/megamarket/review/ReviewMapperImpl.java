package ru.example.megamarket.review;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.example.megamarket.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-09T16:01:51+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewResponse reviewToReviewResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse reviewResponse = new ReviewResponse();

        reviewResponse.setSellerId( reviewSellerId( review ) );
        reviewResponse.setBuyerId( reviewBuyerId( review ) );
        reviewResponse.setId( review.getId() );
        reviewResponse.setText( review.getText() );
        reviewResponse.setRating( review.getRating() );

        return reviewResponse;
    }

    @Override
    public Review reviewRequestToReview(ReviewRequest request) {
        if ( request == null ) {
            return null;
        }

        Review.ReviewBuilder review = Review.builder();

        review.text( request.getText() );
        review.rating( request.getRating() );

        return review.build();
    }

    private Integer reviewSellerId(Review review) {
        if ( review == null ) {
            return null;
        }
        User seller = review.getSeller();
        if ( seller == null ) {
            return null;
        }
        Integer id = seller.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer reviewBuyerId(Review review) {
        if ( review == null ) {
            return null;
        }
        User buyer = review.getBuyer();
        if ( buyer == null ) {
            return null;
        }
        Integer id = buyer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
