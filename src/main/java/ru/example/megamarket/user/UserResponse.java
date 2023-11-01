package ru.example.megamarket.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private Long balance;
    private Integer rating;
}
