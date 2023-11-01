package ru.example.megamarket.image;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "listing.id", source = "request.listingId")
    Image imageRequestToImage(ImageRequest request);

    @Mapping(target = "listingId", source = "image.listing.id")
    ImageResponse imageToImageResponse(Image image);
}
