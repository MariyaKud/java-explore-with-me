package ru.practicum.explorewithme.service.category;

import ru.practicum.explorewithme.dto.in.create.NewCategoryDto;
import ru.practicum.explorewithme.dto.out.CategoryDto;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto newCategoryDto);
}
