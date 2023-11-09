package ru.example.megamarket.listing;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "listing", description = "Работа с объявлениями")
public class ListingController {
    private final ListingService service;
    private final ListingMapper mapper;



    @GetMapping("/search")
    @Operation(description = "Поиск объявлений")
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
    @Operation(description = "Просмотр своих актуальных объявлений")
    public List<ListingResponse> getListings(Principal connectedUser) {
        return service.getAllUserListings(connectedUser)
                .stream()
                .map(mapper::listingToListingResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Operation(description = "Создание объявления")
    public ResponseEntity<Void> createListing(@RequestBody @Valid ListingRequest request, Principal connectedUser) {
        service.addListing(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    // Добавить логику с orders
    @PostMapping("/{listingId}")
    @Operation(description = "Покупка объявления")
    public ResponseEntity<Void> buyListing(@PathVariable Integer listingId, Principal connectedUser) {
        service.buyListingWithId(listingId, connectedUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{listingId}")
    @Operation(description = "Удаление объявления")
    public ResponseEntity<Void> removeListing(@PathVariable Integer listingId, Principal connectedUser) {
        service.deleteListing(listingId, connectedUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{listingId}")
    @Operation(description = "Получение информации об определенном объявлении")
    public ListingResponse getListing(@PathVariable Integer listingId) {
        return mapper.listingToListingResponse(service.getListing(listingId));
    }

    @GetMapping("/userListings/{userId}")
    @Operation(description = "Получение активных объявлений пользователя")
    public List<ListingResponse> getAllUserActiveListings(@PathVariable Integer userId) {
        return service.getAllUserListings(userId)
                .stream()
                .map(mapper::listingToListingResponse)
                .collect(Collectors.toList());
    }
}
