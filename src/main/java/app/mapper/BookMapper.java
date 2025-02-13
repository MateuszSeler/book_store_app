package app.mapper;

import app.config.MapperConfig;
import app.dto.book.BookCreateRequestDto;
import app.dto.book.BookDto;
import app.dto.book.BookDtoWithoutCategoriesIds;
import app.model.Book;

import app.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;

@Mapper(config = MapperConfig.class, uses = {CategoryMapper.class})
public interface BookMapper {
    @Named("fromBookToBookDto")
    @Mapping(source = "categories", target = "categoriesNames",
            qualifiedByName = "fromCategoriesToCategoriesNames")

    BookDto toDto(Book book);

    BookDtoWithoutCategoriesIds toDtoWithoutCategoriesIds(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(BookCreateRequestDto bookCreateRequestDto);

}
