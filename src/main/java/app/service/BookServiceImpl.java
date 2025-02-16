package app.service;

import app.dto.book.BookCreateRequestDto;
import app.dto.book.BookDto;
import app.dto.book.BookDtoWithoutCategoriesIds;
import app.dto.book.BookSearchParametersDto;
import app.exception.EntityNotFoundException;
import app.mapper.BookMapper;
import app.model.Book;
import app.repository.book.BookRepository;
import app.repository.book.BookSpecificationBuilder;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public BookDto addBook(@Valid BookCreateRequestDto bookCreateRequestDto) {
        return bookMapper.toDto(
                bookRepository.save(toEntity(bookCreateRequestDto)));
    }

    @Override
    @Transactional(readOnly = true)
    public BookDtoWithoutCategoriesIds getById(@NonNull Long id) {
        return bookMapper.toDtoWithoutCategoriesIds(
                bookRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Book with id: " + id + " not found")
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDtoWithoutCategoriesIds> search(
            BookSearchParametersDto parameters, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(parameters);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategoriesIds)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDtoWithoutCategoriesIds> findAllByCategoryId(
            @NonNull Long categoryId, Pageable pageable) {
        List<BookDtoWithoutCategoriesIds> booksDtoList = new ArrayList<>();
        for (Book book : bookRepository.findAllByCategoryId(categoryId)) {
            booksDtoList.add(bookMapper.toDtoWithoutCategoriesIds(book));
        }
        return booksDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDtoWithoutCategoriesIds> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategoriesIds)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(@NonNull Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with id: " + id + " not found");
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookDto update(@NonNull Long id, @Valid BookCreateRequestDto bookCreateRequestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id: " + id + " not found")
        );

        Book bookForUpdate = toEntity(bookCreateRequestDto);

        book.setTitle(bookForUpdate.getTitle())
                .setAuthor(bookForUpdate.getAuthor())
                .setIsbn(bookForUpdate.getIsbn())
                .setPrice(bookForUpdate.getPrice())
                .setDescription(bookForUpdate.getDescription())
                .setCoverImage(bookForUpdate.getCoverImage())
                .setCategories(bookForUpdate.getCategories());

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    @Transactional(readOnly = true)
    public Book toEntity(BookCreateRequestDto bookCreateRequestDto) {
        Book book = bookMapper.toModel(bookCreateRequestDto);
        book.setCategories(
                categoryService.getCategoriesFromCategoriesIds(
                        bookCreateRequestDto.getCategoriesIds()));

        return book;
    }
}
