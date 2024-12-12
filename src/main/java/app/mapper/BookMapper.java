package app.mapper;

import app.config.MapperConfig;
import app.dto.BookDto;
import app.dto.CreatBookRequestDto;
import app.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreatBookRequestDto creatBookRequestDto);

}