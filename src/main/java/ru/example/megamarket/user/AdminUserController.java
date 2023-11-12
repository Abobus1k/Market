package ru.example.megamarket.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "admin_user", description = "Работа с пользователями для админа")
public class AdminUserController {

    private final UserService service;
    private final UserMapper mapper;

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("{userId}")
    @Operation(description = "Удаления пользователя")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        service.removeUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    @Operation(description = "Просмотр всех пользователей")
    public List<UserResponse> getAllUsers(@RequestParam Integer offset, @RequestParam Integer limit) {
        return service.getAllUsers(PageRequest.of(offset, limit))
                .stream()
                .map(mapper::userToUserResponse)
                .toList();
    }
}
