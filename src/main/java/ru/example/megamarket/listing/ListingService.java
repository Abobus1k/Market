package ru.example.megamarket.listing;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.example.megamarket.category.Category;
import ru.example.megamarket.category.CategoryRepository;
import ru.example.megamarket.exceptions.localexceptions.InsufficientFundsException;
import ru.example.megamarket.exceptions.localexceptions.ListingAlreadyPurchaseException;
import ru.example.megamarket.order.Order;
import ru.example.megamarket.order.OrderRepository;
import ru.example.megamarket.order.OrderStatus;
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
    private static final String POST_DATE = "postDate";

    public List<Listing> getSearchListings(Principal connectedUser, Integer categoryId, String sortBy, Boolean asc, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<Listing> listings = categoryId == null ?
                repository.findBySold(false, pageRequest).getContent() :
                repository.findByCategoryAndSold(categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("Категории с id: " + categoryId + " не существует")),
                        false, pageRequest).getContent();



        if (sortBy == null) {
            return listings;
        }

        if (sortBy.equals(POST_DATE)) {
            return listings.stream()
                    .sorted(Comparator.comparing(Listing::getPostDate, asc ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        }

        return listings.stream()
                .sorted(Comparator.comparing(Listing::getPrice, asc ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public List<Listing> getAllUserListings(Principal connectedUser, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySoldAndUser(false, user, pageRequest).getContent();
    }

    public List<Listing> getAllUserListings(Integer userId, PageRequest pageRequest) {
        return repository.findBySoldAndUser(false, userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с id: " + userId + " не существует")), pageRequest).getContent();
    }

    public Listing addListing(ListingRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Listing listing = mapper.ListingRequestToListing(request);
        listing.setPostDate(LocalDateTime.now());
        listing.setSold(false);
        listing.setUser(user);
        return repository.save(listing);
    }

    public Listing buyListingWithId(Integer listingId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Listing listing = repository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Объявления с id: " + listingId + " не существует"));

        if (listing.getSold()) {
            throw new ListingAlreadyPurchaseException("Объявление c id: " + listingId + " уже продано");
        }

        if (user.getBalance() < listing.getPrice()) {
            throw new InsufficientFundsException("Недостаточно средст для покупки");
        }

        listing.setSold(true);
        user.setBalance(user.getBalance() - listing.getPrice());

        User seller = userRepository.findById(listing.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("Объявление привязано к несуществуещему пользователю"));

        Order order = new Order();
        order.setListing(listing);
        order.setBuyer(user);
        order.setSeller(seller);
        order.setSum(listing.getPrice());
        order.setStatus(OrderStatus.AWAITING_CONFIRMATION);
        orderRepository.save(order);

        userRepository.save(user);
        return repository.save(listing);
    }

    public void deleteListing(Integer listingId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Listing listing = repository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Объявления с id: " + listingId + " не существует"));
        if (listing.getUser().getId().equals(user.getId())) {
            repository.delete(listing);
        }
    }

    public Listing getListing(Integer listingId) {
        return repository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Объявления с id: " + listingId + " не существует"));
    }

    public void deleteListing(Integer listingId) {
        repository.delete(repository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Объявления с id: " + listingId + " не существует")));
    }
}
