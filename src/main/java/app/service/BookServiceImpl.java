package app.service;

import app.dto.book.BookCreateRequestDto;
import app.dto.book.BookDto;
import app.dto.book.BookDtoWithoutCategoriesIds;
import app.dto.book.BookSearchParametersDto;
import app.exception.EntityNotFoundException;
import app.mapper.BookMapper;
import app.model.Book;
import app.model.Category;
import app.repository.book.BookRepository;
import app.repository.book.BookSpecificationBuilder;
import app.repository.category.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto addBook(BookCreateRequestDto bookCreateRequestDto) {
        Book book = bookMapper.toModel(bookCreateRequestDto, categoryRepository);

        for (Category category : book.getCategories()) {
            Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
            if (optionalCategory.isEmpty()) {
                throw new EntityNotFoundException(
                        "Category with id: " + category.getId() + " not found");
            }
        }

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDtoWithoutCategoriesIds findById(Long id) {
        return bookMapper.toDtoWithoutCategoriesIds(
                bookRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Book with id: " + id + " not found")
        ));
    }

    @Override
    public List<BookDtoWithoutCategoriesIds> search(
            BookSearchParametersDto parameters, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(parameters);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategoriesIds)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoriesIds> findAllByCategoryId(
            Long categoryId, Pageable pageable) {
        List<BookDtoWithoutCategoriesIds> booksDtoList = new ArrayList<>();
        for (Book book : bookRepository.findAllByCategoryId(categoryId)) {
            booksDtoList.add(bookMapper.toDtoWithoutCategoriesIds(book));
        }
        return booksDtoList;
    }

    @Override
    public List<BookDtoWithoutCategoriesIds> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategoriesIds)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void update(Long id, BookCreateRequestDto bookCreateRequestDto) {
        Book book = bookMapper.toModel(bookCreateRequestDto, categoryRepository);
        book.setId(id);
        bookRepository.save(book);
    }
}
