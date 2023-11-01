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
@Table(name = "_listing")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;

    private String text;

    private Integer price;

    private String city;

    private LocalDateTime postDate;

    private Boolean sold;

    @OneToMany(mappedBy = "listing", cascade = {CascadeType.REMOVE})
    private List<Image> images;

    @OneToMany(mappedBy = "listing", cascade = {CascadeType.REMOVE})
    private List<Order> orders;
}
