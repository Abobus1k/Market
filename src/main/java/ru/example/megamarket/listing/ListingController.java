package ru.example.megamarket.listing;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.megamarket.exceptions.localexceptions.ImpossibleSearchException;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
@Tag(name = "listing", description = "Работа с объявлениями")
public class ListingController {
    private final ListingService service;
    private final ListingMapper mapper;
    private static final String PRICE = "price";
    private static final String POST_DATE = "postDate";




    @GetMapping("/search")
    @Operation(description = "Поиск объявлений")
    public PagedListingResponse getSearchListings(
            Principal connectedUser,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Boolean asc,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        if (!isValidRequest(sortBy, asc)) {
            throw new ImpossibleSearchException("Такая сортировка невозможна");
        }

        Page<Listing> page = service.getSearchListings(connectedUser, categoryId, sortBy, asc, PageRequest.of(offset, limit));
        return PagedListingResponse.builder()
                .totalPages(page.getTotalPages())
                .listingResponseList(page.getContent().stream().map(mapper::listingToListingResponse).toList())
                .build();
    }

    @GetMapping("/myListings")
    @Operation(description = "Просмотр своих актуальных объявлений")
    public PagedListingResponse getListings(
            Principal connectedUser,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Page<Listing> page = service.getAllUserListings(connectedUser, PageRequest.of(offset, limit));
        return PagedListingResponse.builder()
                .totalPages(page.getTotalPages())
                .listingResponseList(page.getContent().stream().map(mapper::listingToListingResponse).toList())
                .build();
    }

    @PostMapping
    @Operation(description = "Создание объявления")
    public ListingResponse createListing(@RequestBody @Valid ListingRequest request, Principal connectedUser) {
        return mapper.listingToListingResponse(service.addListing(request, connectedUser));
    }

    // Добавить логику с orders
    @PostMapping("/{listingId}")
    @Operation(description = "Покупка объявления")
    public ListingResponse buyListing(@PathVariable Integer listingId, Principal connectedUser) {
        return mapper.listingToListingResponse(service.buyListingWithId(listingId, connectedUser));
    }

    @PostMapping("/delete/{listingId}")
    @Operation(description = "Удаление объявления")
    public ListingResponse removeListing(@PathVariable Integer listingId, Principal connectedUser) {
        return mapper.listingToListingResponse(service.banListing(listingId, connectedUser));
    }

    @GetMapping("/{listingId}")
    @Operation(description = "Получение информации об определенном объявлении")
    public ListingResponse getListing(@PathVariable Integer listingId) {
        return mapper.listingToListingResponse(service.getListing(listingId));
    }

    @GetMapping("/userListings/{userId}")
    @Operation(description = "Получение активных объявлений пользователя")
    public PagedListingResponse getAllUserActiveListings(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Page<Listing> page = service.getAllUserListings(userId, PageRequest.of(offset, limit));
        return PagedListingResponse.builder()
                .totalPages(page.getTotalPages())
                .listingResponseList(page.getContent().stream().map(mapper::listingToListingResponse).toList())
                .build();
    }

    private boolean isValidRequest(String sortBy, Boolean asc) {
        if (sortBy == null && asc != null || sortBy != null && asc == null) {
            return false;
        }

        return sortBy == null || sortBy.equals(PRICE) || sortBy.equals(POST_DATE);
    }
}
