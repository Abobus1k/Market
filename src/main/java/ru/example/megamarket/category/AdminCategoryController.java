package ru.example.megamarket.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "admin_category", description = "Работа с категориями для админа")
public class AdminCategoryController {
    private final CategoryService service;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Operation(description = "Создание категории")
    public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryRequest request) {
        service.addCategory(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(description = "Удаление категории")
    public ResponseEntity<Void> removeCategory(@PathVariable Integer categoryId) {
        service.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
