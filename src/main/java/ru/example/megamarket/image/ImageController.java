package ru.example.megamarket.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Tag(name = "image", description = "Работа с фотографиями")
public class ImageController {
    private final ImageService service;
    private final ImageMapper mapper;

    @PostMapping("/{listingId}")
    @Operation(description = "Добавление изображения к объявлению")
    public ImageResponse uploadImage(@RequestBody @Valid ImageRequest request,
                                            @PathVariable Integer listingId,
                                            Principal connectedUser) {
        request.setListingId(listingId);
        return mapper.imageToImageResponse(service.addImage(request, listingId, connectedUser));
    }

    @GetMapping("/{listingId}")
    @Operation(description = "Просмотр изображений объявления")
    public List<ImageResponse> getListingImages(@PathVariable Integer listingId) {
        return service.getImages(listingId).stream()
                .map(mapper::imageToImageResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/watch/{imageId}")
    @Operation(description = "Просмотр определенного изображения")
    public ImageResponse checkImage(@PathVariable Integer imageId) {
        return mapper.imageToImageResponse(service.getImage(imageId));
    }

    @DeleteMapping("/{imageId}")
    @Operation(description = "Удаление изображения")
    public ResponseEntity<Void> removeImage(@PathVariable Integer imageId, Principal connectedUser) {
        service.deleteImage(imageId, connectedUser);
        return ResponseEntity.noContent().build();
    }
}
