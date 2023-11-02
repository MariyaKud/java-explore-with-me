package ru.practicum.explorewithme.service.category;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.in.create.NewCategoryDto;
import ru.practicum.explorewithme.dto.out.CategoryDto;
import ru.practicum.explorewithme.model.Category;

@Component
public class CategoryMapper {
    public CategoryDto toDto(Category user) {
        return new CategoryDto(user.getId(), user.getName());
    }

    public Category fromDto(NewCategoryDto dto) {
        return new Category(null, dto.getName());
    }
}
