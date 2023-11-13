package ru.example.megamarket.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public PagedOrderResponse checkUserApprovedBuys(
            Principal connectedUser,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Page<Order> page = service.getAllUserBuyOrders(connectedUser, PageRequest.of(offset, limit));
        return PagedOrderResponse.builder()
                .totalPages(page.getTotalPages())
                .orderResponseList(page.getContent().stream().map(mapper::orderToOrderResponse).toList())
                .build();
    }

    @GetMapping("/approved-sells")
    @Operation(description = "Просмотр продаж текущего пользователя")
    public PagedOrderResponse checkUserApprovedSells(
            Principal connectedUser,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Page<Order> page = service.getAllUserSellOrders(connectedUser, PageRequest.of(offset, limit));
        return PagedOrderResponse.builder()
                .totalPages(page.getTotalPages())
                .orderResponseList(page.getContent().stream().map(mapper::orderToOrderResponse).toList())
                .build();
    }

    @GetMapping("/active-buys")
    @Operation(description = "Просмотр сделок по покупкам текущего пользователя")
    public PagedOrderResponse checkUserActiveBuys(
            Principal connectedUser,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Page<Order> page = service.getAllUserBuyDeals(connectedUser, PageRequest.of(offset, limit));
        return PagedOrderResponse.builder()
                .totalPages(page.getTotalPages())
                .orderResponseList(page.getContent().stream().map(mapper::orderToOrderResponse).toList())
                .build();
    }

    @GetMapping("/active-sells")
    @Operation(description = "Просмотр сделок по продажам текущего пользователя")
    public PagedOrderResponse checkUserActiveSells(
            Principal connectedUser,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Page<Order> page = service.getAllUserSellDeals(connectedUser, PageRequest.of(offset, limit));
        return PagedOrderResponse.builder()
                .totalPages(page.getTotalPages())
                .orderResponseList(page.getContent().stream().map(mapper::orderToOrderResponse).toList())
                .build();
    }

    @GetMapping("/disapprove-buys")
    @Operation(description = "Просмотр сделок по покупкам текущего пользователя")
    public PagedOrderResponse checkUserDisapproveBuys(
            Principal connectedUser,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Page<Order> page = service.getAllUserBuyRejectDeals(connectedUser, PageRequest.of(offset, limit));
        return PagedOrderResponse.builder()
                .totalPages(page.getTotalPages())
                .orderResponseList(page.getContent().stream().map(mapper::orderToOrderResponse).toList())
                .build();
    }

    @GetMapping("/disapprove-sells")
    @Operation(description = "Просмотр сделок по продажам текущего пользователя")
    public PagedOrderResponse checkUserDisapproveSells(
            Principal connectedUser,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Page<Order> page = service.getAllUserSellRejectDeals(connectedUser, PageRequest.of(offset, limit));
        return PagedOrderResponse.builder()
                .totalPages(page.getTotalPages())
                .orderResponseList(page.getContent().stream().map(mapper::orderToOrderResponse).toList())
                .build();
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
