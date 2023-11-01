package ru.example.megamarket.withdraw;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WithdrawMapper {
    Withdraw withdrawRequestToWithdraw(WithdrawRequest request);

    @Mapping(target = "userId", source = "withdraw.user.id")
    WithdrawResponse withdrawToWithdrawResponse(Withdraw withdraw);
}
