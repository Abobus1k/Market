package ru.example.megamarket.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {
    private final CategoryService service;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest request) {
        service.addCategory(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> removeCategory(@PathVariable Integer categoryId) {
        service.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
