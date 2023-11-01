package ru.example.megamarket.deposit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.example.megamarket.listing.Listing;
import ru.example.megamarket.listing.ListingResponse;

@Mapper(componentModel = "spring")
public interface DepositMapper {
    Deposit depositRequestToDeposit(DepositRequest request);

    @Mapping(target = "userId", source = "deposit.user.id")
    DepositResponse depositToDepositResponse(Deposit deposit);
}
