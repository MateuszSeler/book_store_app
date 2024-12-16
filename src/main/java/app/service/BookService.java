package app.service;

import app.dto.BookDto;
import app.dto.BookSearchParameter;
import app.dto.CreatBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto addBook(CreatBookRequestDto creatBookRequestDto);

    BookDto findById(Long id);

    List<BookDto> getAllBooks();

    void deleteById(Long id);

    void update(Long id, CreatBookRequestDto creatBookRequestDto);

    List<BookDto> search(BookSearchParameter parameters);
}
