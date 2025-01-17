package app.mapper;

import app.config.MapperConfig;
import app.dto.book.BookCreateRequestDto;
import app.dto.book.BookDto;
import app.dto.book.BookDtoWithoutCategoriesIds;
import app.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {CategoryMapper.class})
public interface BookMapper {
    @Mapping(source = "categories", target = "categoriesIds",
            qualifiedByName = "fromCategoriesToCategoriesIds")
    BookDto toDto(Book book);

    BookDtoWithoutCategoriesIds toDtoWithoutCategoriesIds(Book book);

    @Mapping(source = "categoriesIds", target = "categories",
            qualifiedByName = "fromCategoriesIdsToCategories")
    Book toModel(BookCreateRequestDto bookCreateRequestDto);

}
