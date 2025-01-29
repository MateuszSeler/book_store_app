package app.mapper;

import app.config.MapperConfig;
import app.dto.category.CategoryDto;
import app.exception.EntityNotFoundException;
import app.model.Category;
import app.repository.category.CategoryRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryDto categoryDto);

    @Named("fromCategoriesIdsToCategories")
    default Set<Category> categoriesSet(Set<Long> categoriesIds,
                                        @Context CategoryRepository categoryRepository) {
        if (categoriesIds == null || categoriesIds.isEmpty()) {
            return new HashSet<>();
        }
        return categoriesIds
                .stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                        "Category with id: " + id + " not found")))
                .collect(Collectors.toSet());
    }

    @Named("fromCategoriesToCategoriesIds")
    default Set<Long> categoriesIdsSet(Set<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return new HashSet<>();
        }
        return categories
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }
}
