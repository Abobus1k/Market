package ru.example.megamarket.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.megamarket.category.Category;
import ru.example.megamarket.listing.Listing;
import ru.example.megamarket.token.Token;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @Column(name = "path")
    private String path;
}
