package app.service;

import app.dto.category.CategoryCreateRequestDto;
import app.dto.category.CategoryDto;
import app.model.Category;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CategoryCreateRequestDto categoryCreateRequestDto);

    CategoryDto update(Long id, CategoryCreateRequestDto categoryCreateRequestDto);

    void deleteById(Long id);

    Set<Category> getCategoriesFromCategoriesIds(Set<Long> categoriesIds);
}
