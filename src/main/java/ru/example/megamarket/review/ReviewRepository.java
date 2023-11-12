package ru.example.megamarket.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.megamarket.user.User;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findBySeller(User seller, PageRequest pageRequest);

    List<Review> findBySeller(User seller);

    List<Review> findBySellerAndBuyer(User seller, User buyer);
}
