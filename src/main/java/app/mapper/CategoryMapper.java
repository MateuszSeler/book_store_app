package app.mapper;

import app.config.MapperConfig;
import app.dto.category.CategoryDto;
import app.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryDto categoryDto);

    @Named("fromCategoriesIdsToCategories")
    default Set<Category> categoriesSet(Set<Long> categoriesIds) {
        if (categoriesIds == null || categoriesIds.isEmpty()) {
            return Set.of();
        }
        return categoriesIds
                .stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }

    @Named("fromCategoriesToCategoriesIds")
    default Set<Long> categoriesIdsSet(Set<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return Set.of();
        }
        return categories
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }
}
