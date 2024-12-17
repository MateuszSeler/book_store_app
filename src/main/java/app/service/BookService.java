package app.service;

import app.dto.BookDto;
import app.dto.BookSearchParametersDto;
import app.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto addBook(CreateBookRequestDto createBookRequestDto);

    BookDto findById(Long id);

    List<BookDto> getAllBooks(Pageable pageable);

    void deleteById(Long id);

    void update(Long id, CreateBookRequestDto createBookRequestDto);

    List<BookDto> search(BookSearchParametersDto searchParameter, Pageable pageable);
}
