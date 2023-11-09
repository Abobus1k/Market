package ru.example.megamarket.image;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.megamarket.listing.Listing;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByListing(Listing listing);

    Boolean existsByListing(Listing listing);
}
