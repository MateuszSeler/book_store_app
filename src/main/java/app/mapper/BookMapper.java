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
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = {CategoryMapper.class})
public interface BookMapper {
    @Mapping(source = "categories.id", target = "categoriesIds")
    BookDto toDto(Book book);

    BookDtoWithoutCategoriesIds toDtoWithoutCategoriesIds(Book book);

    Book toModel(BookCreateRequestDto bookCreateRequestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            Set<Long> longSet = book.getCategories()
                    .stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            bookDto.setCategoriesIds(longSet);
        } else {
            bookDto.setCategoriesIds(Set.of());
        }
    }

}
