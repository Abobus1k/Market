package ru.example.megamarket.listing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.example.megamarket.category.Category;
import ru.example.megamarket.user.User;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer> {
    Page<Listing> findBySoldAndUser(Boolean sold, User user, PageRequest pageRequest);

    Page<Listing> findBySoldAndUserNot(Boolean sold, PageRequest pageRequest, Sort sort, User user);

    Page<Listing> findByCategoryAndSoldAndUserNot(Category category, Boolean sold, PageRequest pageRequest,Sort sort, User user);
}
