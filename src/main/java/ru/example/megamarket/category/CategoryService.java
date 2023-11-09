package ru.example.megamarket.category;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.megamarket.exceptions.localexceptions.DuplicateException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public void addCategory(CategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new DuplicateException("Категория с названием " + request.getName() + " уже существует");
        }
        repository.save(mapper.categoryRequestToCategory(request));
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Category getCategory(Integer categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Категории с id: " + categoryId + " не существует"));
    }

    public void deleteCategory(Integer categoryId) {
        repository.delete(repository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Категории с id: " + categoryId + " не существует")));
    }
}
