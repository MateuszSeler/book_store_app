package app.mapper;

import app.config.MapperConfig;
import app.dto.category.CategoryDto;
import app.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
