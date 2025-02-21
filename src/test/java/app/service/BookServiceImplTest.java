package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.dto.book.BookCreateRequestDto;
import app.exception.EntityNotFoundException;
import app.repository.book.BookRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Test
    void getById_nonExistingBook_EntityNotFound() {
        Long bookId = 31L;

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> bookServiceImpl.getById(bookId));

        assertEquals("Book with id: " + bookId + " not found", exception.getMessage());

    }

    @Test
    void deleteById_nonExistingBook_EntityNotFound() {
        Long bookId = 31L;

        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                bookServiceImpl.deleteById(bookId));

        assertEquals("Book with id: " + bookId + " not found", exception.getMessage());
    }

    @Test
    void update_nonExistingBook_EntityNotFound() {
        Long bookId = 31L;
        BookCreateRequestDto bookCreateRequestDto = new BookCreateRequestDto()
                .setTitle("Lalka")
                .setAuthor("Boleslaw Prus")
                .setIsbn("9781858660653")
                .setPrice(BigDecimal.valueOf(55))
                .setDescription("Lalka")
                .setCoverImage("lalkacover.ai");

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookServiceImpl.update(bookId, bookCreateRequestDto));

        assertEquals("Book with id: " + bookId + " not found", exception.getMessage());
    }
}
