package ru.example.megamarket.listing;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ListingMapper {
    @Mapping(target = "userId", source = "listing.user.id")
    @Mapping(target = "categoryId", source = "listing.category.id")
    ListingResponse listingToListingResponse(Listing listing);

    @Mapping(target = "category.id", source = "request.categoryId")
    Listing ListingRequestToListing(ListingRequest request);
}
