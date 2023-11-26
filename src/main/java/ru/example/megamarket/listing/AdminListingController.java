package ru.example.megamarket.listing;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/listings")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "admin_listings", description = "Работа с объявлениями для админа")
public class AdminListingController {
    private final ListingService service;
    private final ListingMapper mapper;

    @PostMapping("/{listingId}")
    public ListingResponse remove(@PathVariable Integer listingId) {
        return mapper.listingToListingResponse(service.banListing(listingId));
    }
}
