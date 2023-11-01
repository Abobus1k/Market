package ru.example.megamarket.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService service;
    private final UserMapper mapper;

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        service.removeUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return service.getAllUsers()
                .stream()
                .map(mapper::userToUserResponse)
                .collect(Collectors.toList());
    }
}
