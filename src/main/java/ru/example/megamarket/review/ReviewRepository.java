package ru.example.megamarket.review;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.megamarket.user.User;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findBySeller(User seller);

}
