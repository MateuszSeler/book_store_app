package app.mapper;

import app.config.MapperConfig;
import app.dto.book.BookDto;
import app.dto.book.CreateBookRequestDto;
import app.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);

}
