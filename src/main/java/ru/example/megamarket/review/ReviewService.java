package ru.example.megamarket.review;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.example.megamarket.exceptions.localexceptions.ExtraReviewException;
import ru.example.megamarket.order.OrderRepository;
import ru.example.megamarket.user.User;
import ru.example.megamarket.user.UserRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final ReviewMapper mapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public void addReview(ReviewRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с id: " + request.getSellerId() + " не существует"));
        Review review = mapper.reviewRequestToReview(request);
        review.setBuyer(user);
        review.setSeller(seller);

        int numberOfCrossOrders = orderRepository.findBySellerAndBuyer(seller, user).size();
        int numberOfCrossReviews = repository.findBySellerAndBuyer(seller, user).size();

        if (numberOfCrossReviews == numberOfCrossOrders) {
            throw new ExtraReviewException("Нельзя оставить отзыв текущему пользователю");
        }

        if (seller.getRating() == null) {
            seller.setRating(0);
        }

        Integer numberOfSellerReviews = repository.findBySeller(seller).size();

        seller.setRating((seller.getRating() * numberOfSellerReviews + request.getRating()) / (numberOfSellerReviews + 1));
        userRepository.save(seller);
        repository.save(review);
    }

    public List<Review> getAllSellerReviews(Integer sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с id: " + sellerId + " не существует"));
        return repository.findBySeller(seller);
    }

    public List<Review> getAllSellerReviews(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return repository.findBySeller(user);
    }
}
