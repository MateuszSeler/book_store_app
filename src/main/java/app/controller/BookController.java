package app.controller;

import app.dto.BookDto;
import app.dto.BookSearchParameter;
import app.dto.CreatBookRequestDto;
import app.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/search")
    public List<BookDto> search(BookSearchParameter parameter) {
        return bookService.search(parameter);
    }

    @PostMapping
    public BookDto add(@RequestBody CreatBookRequestDto creatBookRequestDto) {
        return bookService.addBook(creatBookRequestDto);
    }

    @PutMapping("/{id}")
    public void put(@PathVariable Long id, @RequestBody CreatBookRequestDto creatBookRequestDto) {
        bookService.update(id, creatBookRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
