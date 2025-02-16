package app.service;

import app.dto.category.CategoryCreateRequestDto;
import app.exception.EntityNotFoundException;
import app.repository.category.CategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
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

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            categoryService.getById(categoryId);
        });
    }

    @Test
    void save() {
    }

    @Test
    void update_nonExistingCategory_EntityNotFound() {
        Long categoryId = 31L;
        CategoryCreateRequestDto categoryCreateRequestDto = new CategoryCreateRequestDto()
                .setName("Polish Literature")
                .setDescription("Polish Literature");

        Mockito.when(categoryRepository
                .findById(categoryId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            categoryService.update(categoryId, categoryCreateRequestDto);
        });
    }

    @Test
    void deleteById_nonExistingCategory_EntityNotFound() {
        Long categoryId = 31L;

        Mockito.when(categoryRepository
                .findById(categoryId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            categoryService.deleteById(categoryId);
        });

    }
}
