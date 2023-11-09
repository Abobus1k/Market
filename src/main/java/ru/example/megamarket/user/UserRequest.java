package ru.example.megamarket.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotEmpty(message = "Не заполнено имя")
    @Size(min = 3, max = 20, message = "Недопустимая длина имени")
    private String firstname;

    @NotEmpty(message = "Не заполнена фамилия")
    @Size(min = 3, max = 20, message = "Недопустимая длина фамилии")
    private String lastname;

    @NotEmpty(message = "Не заполнен email")
    @Email(message = "Некорректная почта")
    private String email;

    @NotEmpty(message = "Не заполнен пароль")
    @Size(min = 8, message = "Длина пароля должна быть не менее 8")
    String password;
}
