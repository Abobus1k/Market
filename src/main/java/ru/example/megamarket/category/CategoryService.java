package ru.example.megamarket.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public void addCategory(CategoryRequest request) {
        repository.save(mapper.categoryRequestToCategory(request));
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Category getCategory(Integer categoryId) {
        return repository.findById(categoryId).orElseThrow();
    }

    public void deleteCategory(Integer categoryId) {
        repository.delete(repository.findById(categoryId).orElseThrow());
    }
}
