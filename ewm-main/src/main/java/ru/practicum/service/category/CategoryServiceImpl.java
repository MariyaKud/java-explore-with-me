package ru.practicum.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.input.create.NewCategoryDto;
import ru.practicum.dto.output.CategoryDto;
import ru.practicum.exeption.NotFoundException;
import ru.practicum.exeption.NotMeetRulesException;
import ru.practicum.model.Category;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements AdminCategoryService, PublicCategoryService {
    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        final Category newCategory = categoryMapper.fromDto(newCategoryDto);
        categoryRepository.save(newCategory);
        return categoryMapper.toDto(newCategory);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long catId, NewCategoryDto updCategoryDto) {
        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                                               new NotFoundException(catId, Category.class));
        category.setName(updCategoryDto.getName());
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public Boolean deleteCategoryById(long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                                               new NotFoundException(catId, Category.class));

        if (eventRepository.existsByCategoryId(catId)) {
            throw new NotMeetRulesException(String.format("There are events related to the category with id=%s", catId));
        }

        categoryRepository.delete(category);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from  / size : 0, size, Sort.by("id"));

        return categoryRepository.findAll(pageable)
                                 .stream()
                                 .map(categoryMapper::toDto)
                                 .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findByIdCategory(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                                               new NotFoundException(catId, Category.class));
        return categoryMapper.toDto(category);
    }
}
