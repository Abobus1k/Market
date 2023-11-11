package ru.example.megamarket.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "order", description = "Работа с историй покупок и продаж")
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;

    @GetMapping("/approved-buys")
    @Operation(description = "Просмотр покупок текущего пользователя")
    public List<OrderResponse> checkUserApprovedBuys(Principal connectedUser) {
        return service.getAllUserBuyOrders(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/approved-sells")
    @Operation(description = "Просмотр продаж текущего пользователя")
    public List<OrderResponse> checkUserApprovedSells(Principal connectedUser) {
        return service.getAllUserSellOrders(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/active-buys")
    @Operation(description = "Просмотр сделок по покупкам текущего пользователя")
    public List<OrderResponse> checkUserActiveBuys(Principal connectedUser) {
        return service.getAllUserBuyDeals(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/active-sells")
    @Operation(description = "Просмотр сделок по продажам текущего пользователя")
    public List<OrderResponse> checkUserActiveSells(Principal connectedUser) {
        return service.getAllUserSellDeals(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/disapprove-buys")
    @Operation(description = "Просмотр сделок по покупкам текущего пользователя")
    public List<OrderResponse> checkUserDisapproveBuys(Principal connectedUser) {
        return service.getAllUserBuyRejectDeals(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/disapprove-sells")
    @Operation(description = "Просмотр сделок по продажам текущего пользователя")
    public List<OrderResponse> checkUserDisapproveSells(Principal connectedUser) {
        return service.getAllUserSellRejectDeals(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/approve/{orderId}")
    public OrderResponse approveOrder(@PathVariable Integer orderId, Principal connectedUser) {
        return mapper.orderToOrderResponse(service.approveDeal(orderId, connectedUser));
    }

    @PostMapping("/disapprove/{orderId}")
    public OrderResponse disapproveOrder(@PathVariable Integer orderId, Principal connectedUser) {
        return mapper.orderToOrderResponse(service.disapproveDeal(orderId, connectedUser));
    }
}
