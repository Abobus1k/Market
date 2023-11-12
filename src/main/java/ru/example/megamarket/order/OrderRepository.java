package ru.example.megamarket.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.megamarket.user.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findBySellerAndBuyerAndStatus(User seller, User buyer, OrderStatus status);

    Page<Order> findBySellerAndStatus(User seller, OrderStatus status, PageRequest pageRequest);

    Page<Order> findByBuyerAndStatus(User buyer, OrderStatus status, PageRequest pageRequest);
}
