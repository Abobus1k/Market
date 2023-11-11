package ru.example.megamarket.listing;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/listings")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "admin_listings", description = "Работа с объявлениями для админа")
public class AdminListingController {
    private final ListingService service;

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> remove(@PathVariable Integer listingId) {
        service.deleteListing(listingId);
        return ResponseEntity.noContent().build();
    }
}
