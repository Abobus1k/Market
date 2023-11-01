package ru.example.megamarket.category;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse categoryToCategoryResponse(Category user);
    Category categoryRequestToCategory(CategoryRequest categoryRequest);
}
