package ru.example.megamarket.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "category", description = "Работа с категориями")
public class CategoryController {
    private final CategoryMapper mapper;
    private final CategoryService service;

    @GetMapping
    @Operation(description = "Просмотр всех категорий")
    public List<CategoryResponse> checkAllCategories() {
        return service.getAllCategories()
                .stream()
                .map(mapper::categoryToCategoryResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{categoryId}")
    @Operation(description = "Просмотр определенной категории")
    public CategoryResponse checkCategory(@PathVariable Integer categoryId) {
        return mapper.categoryToCategoryResponse(service.getCategory(categoryId));
    }
}
