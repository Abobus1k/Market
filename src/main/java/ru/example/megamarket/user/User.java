package ru.example.megamarket.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.example.megamarket.deposit.Deposit;
import ru.example.megamarket.listing.Listing;
import ru.example.megamarket.token.Token;
import ru.example.megamarket.withdraw.Withdraw;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String firstname;
    private String lastname;
    @Column(name = "email", unique = true)
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private Integer balance;
    private Integer rating;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<Token> tokens;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Listing> listings;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Deposit> deposits;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Withdraw> withdraws;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
