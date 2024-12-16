package app.service;

import app.dto.BookDto;
import app.dto.BookSearchParameterDto;
import app.dto.CreatBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto addBook(CreatBookRequestDto createBookRequestDto);

    BookDto findById(Long id);

    List<BookDto> getAllBooks();

    void deleteById(Long id);

    void update(Long id, CreatBookRequestDto creatBookRequestDto);

    List<BookDto> search(BookSearchParameterDto searchParameter);
}
