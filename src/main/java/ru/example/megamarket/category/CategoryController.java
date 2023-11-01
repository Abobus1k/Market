package ru.example.megamarket.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryMapper mapper;
    private final CategoryService service;

    @GetMapping
    public List<CategoryResponse> checkAllCategories() {
        return service.getAllCategories()
                .stream()
                .map(mapper::categoryToCategoryResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse checkCategory(@PathVariable Integer categoryId) {
        return mapper.categoryToCategoryResponse(service.getCategory(categoryId));
    }
}
