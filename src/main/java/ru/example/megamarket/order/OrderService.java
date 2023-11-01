package ru.example.megamarket.order;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.example.megamarket.user.User;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public List<Order> getAllUserBuyOrders(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findByBuyer(user).stream()
                .filter(x -> x.getListing().getSold())
                .collect(Collectors.toList());
    }

    public List<Order> getAllUserSellOrders(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySeller(user).stream()
                .filter(x -> x.getListing().getSold())
                .collect(Collectors.toList());
    }
}
