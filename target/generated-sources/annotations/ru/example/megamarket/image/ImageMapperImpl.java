package ru.example.megamarket.image;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.example.megamarket.listing.Listing;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-09T15:55:25+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public Image imageRequestToImage(ImageRequest request) {
        if ( request == null ) {
            return null;
        }

        Image.ImageBuilder image = Image.builder();

        image.listing( imageRequestToListing( request ) );
        image.path( request.getPath() );

        return image.build();
    }

    @Override
    public ImageResponse imageToImageResponse(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageResponse imageResponse = new ImageResponse();

        imageResponse.setListingId( imageListingId( image ) );
        imageResponse.setId( image.getId() );
        imageResponse.setPath( image.getPath() );

        return imageResponse;
    }

    protected Listing imageRequestToListing(ImageRequest imageRequest) {
        if ( imageRequest == null ) {
            return null;
        }

        Listing.ListingBuilder listing = Listing.builder();

        listing.id( imageRequest.getListingId() );

        return listing.build();
    }

    private Integer imageListingId(Image image) {
        if ( image == null ) {
            return null;
        }
        Listing listing = image.getListing();
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
