package app.controller;

import app.dto.book.BookCreateRequestDto;
import app.dto.book.BookDto;
import app.dto.book.BookDtoWithoutCategoriesIds;
import app.dto.book.BookSearchParametersDto;
import app.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "books manager", description = "Endpoints for managing books in book shop app")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    @Operation(summary = "finding by id",
            description = "finding book by id")
    public BookDtoWithoutCategoriesIds findById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "getting all",
            description = "getting all books")
    public List<BookDtoWithoutCategoriesIds> getAll(Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/search")
    @Operation(summary = "searching by parameters",
            description = "getting books based on parameters")
    public List<BookDtoWithoutCategoriesIds> search(
            BookSearchParametersDto parameter, Pageable pageable) {
        return bookService.search(parameter, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "creating",
            description = "creating new book")
    public ResponseEntity<BookDto> add(
            @RequestBody @Valid BookCreateRequestDto bookCreateRequestDto) {
        BookDto savedbookDto = bookService.addBook(bookCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedbookDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "updating", description = "updating book by id")
    public BookDto update(@PathVariable @Valid Long id,
                    @RequestBody BookCreateRequestDto bookCreateRequestDto) {
        return bookService.update(id, bookCreateRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deleting",
            description = "deleting book by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
