package ru.example.megamarket.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "order", description = "Работа с историй покупок и продаж")
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;

    @GetMapping("/buys")
    @Operation(description = "Просмотр покупок текущего пользователя")
    public List<OrderResponse> checkUserBuys(Principal connectedUser) {
        return service.getAllUserBuyOrders(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/sells")
    @Operation(description = "Просмотр продаж текущего пользователя")
    public List<OrderResponse> checkUserSells(Principal connectedUser) {
        return service.getAllUserSellOrders(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }
}
