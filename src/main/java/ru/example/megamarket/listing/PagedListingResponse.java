package ru.example.megamarket.listing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedListingResponse {
    private Integer totalPages;
    private List<ListingResponse> listingResponseList;
}
