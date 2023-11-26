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
    Page<Listing> findBySoldAndUserAndListingStatus(Boolean sold, User user, PageRequest pageRequest, ListingStatus status);

    Page<Listing> findBySoldAndUserNotAndListingStatus(Boolean sold, PageRequest pageRequest, Sort sort, User user, ListingStatus status);

    Page<Listing> findByCategoryAndSoldAndUserNotAndListingStatus(Category category, Boolean sold, PageRequest pageRequest,Sort sort, User user, ListingStatus status);

    List<Listing> findAllByUser(User user);
}
