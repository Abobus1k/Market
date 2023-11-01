package ru.example.megamarket.review;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "sellerId", source = "review.seller.id")
    @Mapping(target = "buyerId", source = "review.buyer.id")
    ReviewResponse reviewToReviewResponse(Review review);

    Review reviewRequestToReview(ReviewRequest request);
}
