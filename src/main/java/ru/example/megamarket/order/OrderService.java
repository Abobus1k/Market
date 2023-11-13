package ru.example.megamarket.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import ru.example.megamarket.exceptions.localexceptions.ImpossibleDealException;
import ru.example.megamarket.listing.Listing;
import ru.example.megamarket.listing.ListingRepository;
import ru.example.megamarket.user.User;
import ru.example.megamarket.user.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    public Page<Order> getAllUserBuyOrders(Principal connectedUser, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findByBuyerAndStatus(user, OrderStatus.APPROVE, pageRequest);
    }

    public Page<Order> getAllUserSellOrders(Principal connectedUser, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySellerAndStatus(user, OrderStatus.APPROVE, pageRequest);
    }

    public Page<Order> getAllUserBuyDeals(Principal connectedUser, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findByBuyerAndStatus(user, OrderStatus.AWAITING_CONFIRMATION, pageRequest);
    }

    public Page<Order> getAllUserSellDeals(Principal connectedUser, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySellerAndStatus(user, OrderStatus.AWAITING_CONFIRMATION, pageRequest);
    }

    public Page<Order> getAllUserBuyRejectDeals(Principal connectedUser, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findByBuyerAndStatus(user, OrderStatus.DISAPPROVE, pageRequest);
    }

    public Page<Order> getAllUserSellRejectDeals(Principal connectedUser, PageRequest pageRequest) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySellerAndStatus(user, OrderStatus.DISAPPROVE, pageRequest);
    }

    public Order approveDeal(Integer dealId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Order deal = repository.findById(dealId).orElseThrow(() -> new EntityNotFoundException(
                "Сделки с id: " + dealId + " не существует"
        ));

        if (user.getId().equals(deal.getBuyer().getId()) && deal.getStatus().equals(OrderStatus.AWAITING_CONFIRMATION)) {
            deal.setStatus(OrderStatus.APPROVE);
            User seller = deal.getSeller();
            seller.setBalance(seller.getBalance() + deal.getSum());
            userRepository.save(seller);
            return repository.save(deal);
        }
        else {
            throw new ImpossibleDealException("Попытка изменения статуса сделки отклонена");
        }
    }

    public Order disapproveDeal(Integer dealId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Order deal = repository.findById(dealId).orElseThrow(() -> new EntityNotFoundException(
                "Сделки с id: " + dealId + " не существует"
        ));

        if (user.getId().equals(deal.getBuyer().getId()) && deal.getStatus().equals(OrderStatus.AWAITING_CONFIRMATION)) {
            user.setBalance(user.getBalance() + deal.getSum());
            Listing listing = deal.getListing();
            listing.setSold(false);
            deal.setStatus(OrderStatus.DISAPPROVE);
            userRepository.save(user);
            listingRepository.save(listing);
            return repository.save(deal);
        }
        else {
            throw new ImpossibleDealException("Попытка изменения статуса сделки отклонена");
        }
    }
}
