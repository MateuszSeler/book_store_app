package app.service;

import app.dto.BookDto;
import app.dto.CreatBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto addBook(CreatBookRequestDto creatBookRequestDto);

    BookDto findById(Long id);

    List<BookDto> getAllBooks();

    boolean deleteBook(Long id);
}
