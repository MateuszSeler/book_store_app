package app.service;

import app.dto.book.BookCreateRequestDto;
import app.dto.book.BookDto;
import app.dto.book.BookDtoWithoutCategoriesIds;
import app.dto.book.BookSearchParametersDto;
import app.model.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto addBook(BookCreateRequestDto bookCreateRequestDto);

    BookDtoWithoutCategoriesIds getById(Long id);

    List<BookDtoWithoutCategoriesIds> getAllBooks(Pageable pageable);

    void deleteById(Long id);

    BookDto update(Long id, BookCreateRequestDto bookCreateRequestDto);

    List<BookDtoWithoutCategoriesIds> search(
            BookSearchParametersDto searchParameter, Pageable pageable);

    List<BookDtoWithoutCategoriesIds> findAllByCategoryId(Long categoryId, Pageable pageable);

    Book toEntity(BookCreateRequestDto bookCreateRequestDto);
}
