package ru.example.megamarket.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.megamarket.order.Order;
import ru.example.megamarket.order.OrderStatus;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Page<User> findByUserStatus(UserStatus userStatus, PageRequest pageRequest);

}

