package app.service;

import app.dto.book.BookDto;
import app.dto.book.BookSearchParametersDto;
import app.dto.book.CreateBookRequestDto;
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
