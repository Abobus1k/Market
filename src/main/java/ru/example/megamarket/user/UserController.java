package ru.example.megamarket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("/{userId}")
    public UserResponse checkUserProfile(@PathVariable Integer userId) {
        return mapper.userToUserResponse(service.userProfile(userId));
    }

    @PatchMapping
    public ResponseEntity<?> changeProfile(
            @RequestBody UserRequest request,
            Principal connectedUser
    ) {
        service.update(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(
            Principal connectedUser
    ) {
        service.deleteAccount(connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public UserResponse checkProfile(Principal connectedUser) {
        return mapper.userToUserResponse(service.userProfile(connectedUser));
    }
}
