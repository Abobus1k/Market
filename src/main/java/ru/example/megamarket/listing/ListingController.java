package ru.example.megamarket.listing;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/listings")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService service;
    private final ListingMapper mapper;



    @GetMapping("/search")
    public List<ListingResponse> getSearchListings(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Boolean priceOrder,
            @RequestParam(required = false) Boolean postDateOrder
    ) {
        return service.getSearchListings(categoryId, priceOrder, postDateOrder)
                .stream()
                .map(mapper::listingToListingResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/myListings")
    public List<ListingResponse> getListings(Principal connectedUser) {
        return service.getAllUserListings(connectedUser)
                .stream()
                .map(mapper::listingToListingResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Void> createListing(@RequestBody ListingRequest request, Principal connectedUser) {
        service.addListing(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    // Добавить логику с orders
    @PostMapping("/{listingId}")
    public ResponseEntity<Void> buyListing(@PathVariable Integer listingId, Principal connectedUser) {
        service.buyListingWithId(listingId, connectedUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> removeListing(@PathVariable Integer listingId, Principal connectedUser) {
        service.deleteListing(listingId, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{listingId}")
    public ListingResponse getListing(@PathVariable Integer listingId) {
        return mapper.listingToListingResponse(service.getListing(listingId));
    }

    @GetMapping("/userListings/{userId}")
    public List<ListingResponse> getAllUserActiveListings(@PathVariable Integer userId) {
        return service.getAllUserListings(userId)
                .stream()
                .map(mapper::listingToListingResponse)
                .collect(Collectors.toList());
    }
}
