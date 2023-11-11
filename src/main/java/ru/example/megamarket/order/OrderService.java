package ru.example.megamarket.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public List<Order> getAllUserBuyOrders(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findByBuyerAndStatus(user, OrderStatus.APPROVE).stream()
                .filter(x -> x.getListing().getSold())
                .collect(Collectors.toList());
    }

    public List<Order> getAllUserSellOrders(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySellerAndStatus(user, OrderStatus.APPROVE).stream()
                .filter(x -> x.getListing().getSold())
                .collect(Collectors.toList());
    }

    public List<Order> getAllUserBuyDeals(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findByBuyerAndStatus(user, OrderStatus.AWAITING_CONFIRMATION).stream()
                .filter(x -> x.getListing().getSold())
                .collect(Collectors.toList());
    }

    public List<Order> getAllUserSellDeals(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySellerAndStatus(user, OrderStatus.AWAITING_CONFIRMATION).stream()
                .filter(x -> x.getListing().getSold())
                .collect(Collectors.toList());
    }

    public List<Order> getAllUserBuyRejectDeals(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findByBuyerAndStatus(user, OrderStatus.DISAPPROVE).stream()
                .filter(x -> x.getListing().getSold())
                .collect(Collectors.toList());
    }

    public List<Order> getAllUserSellRejectDeals(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySellerAndStatus(user, OrderStatus.DISAPPROVE).stream()
                .filter(x -> x.getListing().getSold())
                .collect(Collectors.toList());
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
