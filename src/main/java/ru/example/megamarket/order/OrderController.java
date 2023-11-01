package ru.example.megamarket.order;

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
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;

    @GetMapping("/buys")
    public List<OrderResponse> checkUserBuys(Principal connectedUser) {
        return service.getAllUserBuyOrders(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/sells")
    public List<OrderResponse> checkUserSells(Principal connectedUser) {
        return service.getAllUserSellOrders(connectedUser)
                .stream()
                .map(mapper::orderToOrderResponse)
                .collect(Collectors.toList());
    }
}
