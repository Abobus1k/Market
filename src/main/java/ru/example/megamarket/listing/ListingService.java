package ru.example.megamarket.listing;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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

    public Page<Listing> getSearchListings(Principal connectedUser, Integer categoryId, String sortBy, Boolean asc, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Sort sort;

        if (sortBy == null) {
            sort = Sort.unsorted();
        } else {
            sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        }

        return categoryId == null ?
                repository.findBySoldAndUserNotAndListingStatus(false, pageRequest, sort, user, ListingStatus.ACTIVE) :
                repository.findByCategoryAndSoldAndUserNotAndListingStatus(categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("Категории с id: " + categoryId + " не существует")),
                        false, pageRequest,sort, user, ListingStatus.ACTIVE);
    }

    public Page<Listing> getAllUserListings(Principal connectedUser, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySoldAndUserAndListingStatus(false, user, pageRequest, ListingStatus.ACTIVE);
    }

    public Page<Listing> getAllUserListings(Integer userId, PageRequest pageRequest) {
        return repository.findBySoldAndUserAndListingStatus(false, userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с id: " + userId + " не существует")), pageRequest, ListingStatus.ACTIVE);
    }

    public Listing addListing(ListingRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Listing listing = mapper.ListingRequestToListing(request);
        listing.setPostDate(LocalDateTime.now());
        listing.setSold(false);
        listing.setListingStatus(ListingStatus.ACTIVE);
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

    public Listing banListing(Integer listingId) {
        Listing listing = repository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Объявления с id: " + listingId + " не существует"));
        listing.setListingStatus(ListingStatus.TRASH);
        return repository.save(listing);
    }

    public Listing banListing(Integer listingId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Listing listing = repository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Объявления с id: " + listingId + " не существует"));
        //listing.setListingStatus(ListingStatus.TRASH);
        //return repository.save(listing);
        if (listing.getUser().getId().equals(user.getId())) {
            listing.setListingStatus(ListingStatus.TRASH);
            return repository.save(listing);
        }

        throw new RuntimeException();
    }
}
