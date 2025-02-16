package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.dto.category.CategoryCreateRequestDto;
import app.exception.EntityNotFoundException;
import app.repository.category.CategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getById_nonExistingCategory_EntityNotFound() {
        Long categoryId = 31L;

        Mockito.when(categoryRepository
                .findById(categoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.getById(categoryId));

        assertEquals("Category with id: " + categoryId
                + " not found", exception.getMessage());
    }

    @Test
    void update_nonExistingCategory_EntityNotFound() {
        Long categoryId = 31L;
        CategoryCreateRequestDto categoryCreateRequestDto = new CategoryCreateRequestDto()
                .setName("Polish Literature")
                .setDescription("Polish Literature");

        Mockito.when(categoryRepository
                .findById(categoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(categoryId, categoryCreateRequestDto));

        assertEquals("Category with id: " + categoryId
                + " not found", exception.getMessage());
    }

    @Test
    void deleteById_nonExistingCategory_EntityNotFound() {
        Long categoryId = 31L;

        Mockito.when(categoryRepository
                .findById(categoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                categoryService.deleteById(categoryId));

        assertEquals("Category with id: " + categoryId
                + " not found", exception.getMessage());
    }
}
