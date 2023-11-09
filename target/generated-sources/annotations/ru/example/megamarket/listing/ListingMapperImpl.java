package ru.example.megamarket.listing;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.example.megamarket.category.Category;
import ru.example.megamarket.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-09T15:55:25+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class ListingMapperImpl implements ListingMapper {

    @Override
    public ListingResponse listingToListingResponse(Listing listing) {
        if ( listing == null ) {
            return null;
        }

        ListingResponse listingResponse = new ListingResponse();

        listingResponse.setUserId( listingUserId( listing ) );
        Integer id1 = listingCategoryId( listing );
        if ( id1 != null ) {
            listingResponse.setCategoryId( String.valueOf( id1 ) );
        }
        listingResponse.setId( listing.getId() );
        listingResponse.setTitle( listing.getTitle() );
        listingResponse.setText( listing.getText() );
        listingResponse.setPrice( listing.getPrice() );
        listingResponse.setPostDate( listing.getPostDate() );
        listingResponse.setSold( listing.getSold() );

        return listingResponse;
    }

    @Override
    public Listing ListingRequestToListing(ListingRequest request) {
        if ( request == null ) {
            return null;
        }

        Listing.ListingBuilder listing = Listing.builder();

        listing.category( listingRequestToCategory( request ) );
        listing.title( request.getTitle() );
        listing.text( request.getText() );
        listing.price( request.getPrice() );

        return listing.build();
    }

    private Integer listingUserId(Listing listing) {
        if ( listing == null ) {
            return null;
        }
        User user = listing.getUser();
        if ( user == null ) {
            return null;
        }
        Integer id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer listingCategoryId(Listing listing) {
        if ( listing == null ) {
            return null;
        }
        Category category = listing.getCategory();
        if ( category == null ) {
            return null;
        }
        Integer id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Category listingRequestToCategory(ListingRequest listingRequest) {
        if ( listingRequest == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( listingRequest.getCategoryId() );

        return category.build();
    }
}
