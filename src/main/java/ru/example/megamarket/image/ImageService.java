package ru.example.megamarket.image;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import ru.example.megamarket.exceptions.localexceptions.UnexpectedImageLoadException;
import ru.example.megamarket.listing.Listing;
import ru.example.megamarket.listing.ListingRepository;
import ru.example.megamarket.user.User;
import ru.example.megamarket.user.UserRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final ImageMapper mapper;

    public Image addImage(ImageRequest request, Integer listingId, Principal connectedUser) {
        var currentUser = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Image image = mapper.imageRequestToImage(request);
        Listing listing = listingRepository.findById(listingId).orElseThrow();
        if (listing.getUser().getId().equals(currentUser.getId())) {
            image.setListing(listing);
            return repository.save(mapper.imageRequestToImage(request));
        }
        else {
            throw new UnexpectedImageLoadException("Попытка загрузить изображение отклонена");
        }
    }

    public Image getImage(Integer imageId) {
        return repository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Изображения с id: " + imageId + " не существует"));
    }

    public List<Image> getImages(Integer listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Объявления с id: " + listingId + " не существует"));

        return repository.findByListing(listing);
    }

    public void deleteImage(Integer imageId, Principal connectedUser) {
        var currentUser = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Image image = repository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Изображения с id: " + imageId + " не существует"));

        Listing listing = listingRepository.findById(image.getListing().getId())
                .orElseThrow(() -> new EntityNotFoundException("Изображение привязано к несуществующему объявлению"));

        User user = userRepository.findById(listing.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("Объявление принадлежит к несуществующему пользователю"));

        if (user.getId().equals(currentUser.getId())) {
            repository.delete(image);
        }
    }
}
