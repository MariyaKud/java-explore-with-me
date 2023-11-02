package ru.practicum.explorewithme.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.in.create.NewCategoryDto;
import ru.practicum.explorewithme.dto.out.CategoryDto;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        final Category newCategory = categoryMapper.fromDto(newCategoryDto);
        categoryRepository.save(newCategory);
        return categoryMapper.toDto(newCategory);
    }
}
