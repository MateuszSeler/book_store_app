package app.controller;

import app.dto.BookDto;
import app.dto.BookSearchParametersDto;
import app.dto.CreateBookRequestDto;
import app.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "book manger", description = "Endpoints for manging books in book shop app")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    @Operation(summary = "finding by id",
            description = "finding book by id")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    @Operation(summary = "getting all",
            description = "getting all books")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "searching by parameters",
            description = "getting books based on parameters")
    public List<BookDto> search(BookSearchParametersDto parameter, Pageable pageable) {
        return bookService.search(parameter, pageable);
    }

    @PostMapping
    @Operation(summary = "creating",
            description = "creating new book")
    public BookDto add(@RequestBody @Valid CreateBookRequestDto createBookRequestDto) {
        return bookService.addBook(createBookRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updating", description = "updating book by id")
    public void put(@PathVariable @Valid Long id,
                    @RequestBody CreateBookRequestDto createBookRequestDto) {
        bookService.update(id, createBookRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "deleting",
            description = "deleting book by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
