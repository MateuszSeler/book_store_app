package app.service;

import app.dto.book.BookCreateRequestDto;
import app.dto.book.BookDto;
import app.dto.book.BookDtoWithoutCategoriesIds;
import app.dto.book.BookSearchParametersDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto addBook(BookCreateRequestDto bookCreateRequestDto);

    BookDtoWithoutCategoriesIds findById(Long id);

    List<BookDtoWithoutCategoriesIds> getAllBooks(Pageable pageable);

    void deleteById(Long id);

    void update(Long id, BookCreateRequestDto bookCreateRequestDto);

    List<BookDtoWithoutCategoriesIds> search(
            BookSearchParametersDto searchParameter, Pageable pageable);

    List<BookDtoWithoutCategoriesIds> findAllByCategoryId(Long categoryId, Pageable pageable);
}
