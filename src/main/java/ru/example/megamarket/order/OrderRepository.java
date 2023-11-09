package ru.example.megamarket.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.megamarket.user.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByBuyer(User buyer);

    List<Order> findBySeller(User seller);

    List<Order> findBySellerAndBuyer(User seller, User buyer);
}
