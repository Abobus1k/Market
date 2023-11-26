package ru.example.megamarket.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "user", description = "Работа с пользователями")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("/{userId}")
    @Operation(description = "Просмотр профиля другого пользователя")
    public UserResponse checkUserProfile(@PathVariable Integer userId) {
        return mapper.userToUserResponse(service.userProfile(userId));
    }

    @PatchMapping
    @Operation(description = "Изменение профиля")
    public UserResponse changeProfile(
            @RequestBody @Valid UserRequest request,
            Principal connectedUser
    ) {
        service.update(request, connectedUser);
        return mapper.userToUserResponse(service.update(request, connectedUser));
    }

    @PostMapping
    @Operation(description = "Удаление аккаунта")
    public UserResponse deleteAccount(
            Principal connectedUser
    ) {
        return mapper.userToUserResponse(service.banAccount(connectedUser));
    }

    @GetMapping("/profile")
    @Operation(description = "Просмотр профиля текущего пользователя")
    public UserResponse checkProfile(Principal connectedUser) {
        return mapper.userToUserResponse(service.userProfile(connectedUser));
    }
}
