package ru.example.megamarket.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService service;
    private final ImageMapper mapper;

    @PostMapping("/{listingId}")
    public ResponseEntity<Void> uploadImage(@RequestBody ImageRequest request,
                                            @PathVariable Integer listingId,
                                            Principal connectedUser) {
        request.setListingId(listingId);
        service.addImage(request, listingId, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{listingId}")
    public List<ImageResponse> getListingImages(@PathVariable Integer listingId) {
        return service.getImages(listingId).stream()
                .map(mapper::imageToImageResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/watch/{imageId}")
    public ImageResponse checkImage(@PathVariable Integer imageId) {
        return mapper.imageToImageResponse(service.getImage(imageId));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> removeImage(@PathVariable Integer imageId, Principal connectedUser) {
        service.deleteImage(imageId, connectedUser);
        return ResponseEntity.ok().build();
    }
}
