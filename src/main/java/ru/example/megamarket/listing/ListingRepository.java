package ru.example.megamarket.listing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.example.megamarket.category.Category;
import ru.example.megamarket.user.User;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer> {
    List<Listing> findBySoldAndUser(Boolean sold, User user);

    List<Listing> findBySold(Boolean sold);

    List<Listing> findByCategoryAndSold(Category category, Boolean sold);
}
