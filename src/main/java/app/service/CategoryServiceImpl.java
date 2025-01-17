package app.service;

import app.dto.category.CategoryDto;
import app.exception.EntityNotFoundException;
import app.mapper.CategoryMapper;
import app.model.Category;
import app.repository.category.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(
                    categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category with id: " + id + " not found")));
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        return categoryMapper.toDto(
                categoryRepository.save(
                        categoryMapper.toEntity(categoryDto)));
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Category with id: " + id + " not found");
        }

        Category categoryForUpdate = categoryMapper.toEntity(categoryDto);
        categoryForUpdate.setId(id);
        return categoryMapper.toDto(categoryRepository.save(categoryForUpdate));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
