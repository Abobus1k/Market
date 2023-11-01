package ru.example.megamarket.deposit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.megamarket.user.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_deposit")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer sum;
}
