package ru.example.megamarket.listing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.megamarket.category.Category;
import ru.example.megamarket.image.Image;
import ru.example.megamarket.order.Order;
import ru.example.megamarket.token.Token;
import ru.example.megamarket.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "price")
    private Integer price;

    @Column(name = "post_date")
    private LocalDateTime postDate;

    @Column(name = "sold")
    private Boolean sold;

    @Enumerated(EnumType.STRING)
    @Column(name = "listing_status")
    private ListingStatus listingStatus;

    @OneToMany(mappedBy = "listing", cascade = {CascadeType.REMOVE})
    private List<Image> images;

    @OneToMany(mappedBy = "listing", cascade = {CascadeType.REMOVE})
    private List<Order> orders;
}
