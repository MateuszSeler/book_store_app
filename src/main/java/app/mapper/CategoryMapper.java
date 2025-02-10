package app.mapper;

import app.config.MapperConfig;
import app.dto.category.CategoryDto;
import app.model.Category;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryDto categoryDto);

    @Named("fromCategoriesToCategoriesNames")
    default Set<String> categoriesNamesSet(Set<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return new HashSet<>();
        }
        return categories
                .stream()
                .map(Category::getName)
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
