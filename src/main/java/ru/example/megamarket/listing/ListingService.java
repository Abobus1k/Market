package ru.example.megamarket.listing;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.example.megamarket.category.Category;
import ru.example.megamarket.category.CategoryRepository;
import ru.example.megamarket.order.Order;
import ru.example.megamarket.order.OrderRepository;
import ru.example.megamarket.user.User;
import ru.example.megamarket.user.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListingService {
    private final ListingRepository repository;
    private final ListingMapper mapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
//    public List<Listing> getSearchListings(Integer categoryId, Boolean priceOrder, Boolean postDateOrder) {
//        if (priceOrder == null && postDateOrder == null) {
//            return categoryId == null ? repository.findBySold(false) :
//                    repository.findByCategoryAndSold(categoryRepository.findById(categoryId).orElseThrow(), false);
//
//        }
//
//        if (priceOrder != null && postDateOrder != null) {
//            return categoryId == null ? repository.findBySold(false)
//                    .stream()
//                    .sorted(Comparator
//                            .comparing(Listing::getPostDate, postDateOrder ? Comparator.naturalOrder() : Comparator.reverseOrder())
//                            .thenComparing(Listing::getPrice, priceOrder ? Comparator.naturalOrder() : Comparator.reverseOrder()))
//                    .collect(Collectors.toList()) :
//                        repository.findByCategoryAndSold(categoryRepository.findById(categoryId).orElseThrow(), false)
//                                .stream()
//                                .sorted(Comparator
//                                        .comparing(Listing::getPostDate, postDateOrder ? Comparator.naturalOrder() : Comparator.reverseOrder())
//                                        .thenComparing(Listing::getPrice, priceOrder ? Comparator.naturalOrder() : Comparator.reverseOrder()))
//                                .collect(Collectors.toList());
//        }
//
//        if (priceOrder != null) {
//            return categoryId == null ? repository.findBySold(false)
//                    .stream()
//                    .sorted(Comparator
//                            .comparing(Listing::getPrice, priceOrder ? Comparator.naturalOrder() : Comparator.reverseOrder()))
//                    .collect(Collectors.toList()) :
//                        repository.findByCategoryAndSold(categoryRepository.findById(categoryId).orElseThrow(), false)
//                                .stream()
//                                .sorted(Comparator
//                                        .comparing(Listing::getPrice, priceOrder ? Comparator.naturalOrder() : Comparator.reverseOrder()))
//                                .collect(Collectors.toList());
//        }
//
//        return categoryId == null ? repository.findBySold(false)
//                .stream()
//                .sorted(Comparator
//                        .comparing(Listing::getPostDate, postDateOrder ? Comparator.naturalOrder() : Comparator.reverseOrder()))
//                .collect(Collectors.toList()) :
//                repository.findByCategoryAndSold(categoryRepository.findById(categoryId).orElseThrow(), false)
//                        .stream()
//                        .sorted(Comparator
//                                .comparing(Listing::getPostDate, postDateOrder ? Comparator.naturalOrder() : Comparator.reverseOrder()))
//                        .collect(Collectors.toList());
//    }

    public List<Listing> getSearchListings(Integer categoryId, Boolean priceOrder, Boolean postDateOrder) {
        List<Listing> listings = categoryId == null ?
                repository.findBySold(false) :
                repository.findByCategoryAndSold(categoryRepository.findById(categoryId).orElseThrow(), false);

        if (postDateOrder != null) {
            return listings.stream()
                    .sorted(Comparator.comparing(Listing::getPostDate))
                    .collect(Collectors.toList());
        }

        if (priceOrder != null) {
            return listings.stream()
                    .sorted(Comparator.comparing(Listing::getPrice))
                    .collect(Collectors.toList());
        }

        return listings;
    }

    public List<Listing> getAllUserListings(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySoldAndUser(false, user);
    }

    public List<Listing> getAllUserListings(Integer userId) {
        return repository.findBySoldAndUser(false, userRepository.findById(userId).orElseThrow());
    }

    public void addListing(ListingRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Listing listing = mapper.ListingRequestToListing(request);
        listing.setPostDate(LocalDateTime.now());
        listing.setSold(false);
        listing.setUser(user);
        repository.save(listing);
    }

    public void buyListingWithId(Integer listingId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Listing listing = repository.findById(listingId).orElseThrow();

        if (user.getBalance() < listing.getPrice() || listing.getSold()) {
            return;
        }

        listing.setSold(true);
        user.setBalance(user.getBalance() - listing.getPrice());

        User seller = userRepository.findById(listing.getUser().getId()).orElseThrow();

        Order order = new Order();
        order.setListing(listing);
        order.setBuyer(user);
        order.setSeller(seller);
        order.setSum(listing.getPrice());
        orderRepository.save(order);

        userRepository.save(user);
        repository.save(listing);
    }

    public void deleteListing(Integer listingId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Listing listing = repository.findById(listingId).orElseThrow();
        if (listing.getUser().getId().equals(user.getId())) {
            repository.delete(listing);
        }
    }

    public Listing getListing(Integer listingId) {
        return repository.findById(listingId).orElseThrow();
    }
}
