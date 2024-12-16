package app.service;

import app.dto.BookDto;
import app.dto.BookSearchParameterDto;
import app.dto.CreatBookRequestDto;
import app.exception.EntityNotFoundException;
import app.mapper.BookMapper;
import app.model.Book;
import app.repository.book.BookRepository;
import app.repository.book.BookSpecificationBuilder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto addBook(CreatBookRequestDto creatBookRequestDto) {
        Book book = bookMapper.toModel(creatBookRequestDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(
                bookRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Book with id: " + id + " not found")
        ));
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void update(Long id, CreatBookRequestDto creatBookRequestDto) {
        Book book = bookMapper.toModel(creatBookRequestDto);
        book.setId(id);
        bookRepository.save(book);
    }

    @Override
    public List<BookDto> search(BookSearchParameterDto parameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(parameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
