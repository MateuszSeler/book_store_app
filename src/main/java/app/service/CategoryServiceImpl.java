package app.service;

import app.dto.category.CategoryDto;
import app.exception.EntityNotFoundException;
import app.mapper.CategoryMapper;
import app.model.Category;
import app.repository.category.CategoryRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(
                    categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category with id: " + id + " not found")));
    }

    @Override
    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        return categoryMapper.toDto(
                categoryRepository.save(
                        categoryMapper.toModel(categoryDto)));
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Category with id: " + id + " not found");
        }

        Category categoryForUpdate = categoryMapper.toModel(categoryDto);
        categoryForUpdate.setId(id);
        return categoryMapper.toDto(categoryRepository.save(categoryForUpdate));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Category with id: " + id + " not found");
        }

        categoryRepository.deleteById(id);
    }

    public Set<Category> getCategoriesFromCategoriesIds(Set<Long> categoriesIds) {
        if (categoriesIds == null || categoriesIds.isEmpty()) {
            return new HashSet<>();
        }
        return categoriesIds
                .stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                        "Category with id: " + id + " not found")))
                .collect(Collectors.toSet());
    }
}
