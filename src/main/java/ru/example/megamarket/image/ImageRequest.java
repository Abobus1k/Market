package ru.example.megamarket.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequest {
    @NotEmpty(message = "Путь до фотографии не заполнен")
    private String path;

    @JsonIgnore
    private Integer listingId;
}
