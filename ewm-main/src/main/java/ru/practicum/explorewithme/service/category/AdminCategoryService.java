package ru.practicum.explorewithme.service.category;

import ru.practicum.explorewithme.dto.input.create.NewCategoryDto;
import ru.practicum.explorewithme.dto.output.CategoryDto;

public interface AdminCategoryService {

    /**
     * Добавление новой категории (имя категории должно быть уникальным)
     *
     * @param newCategoryDto Данные добавляемой категории
     * @return 201 Категория добавлена
     * 400 Запрос составлен некорректно
     * 409 Нарушение целостности данных
     */
    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    /**
     * Изменение категории (имя категории должно быть уникальным)
     *
     * @param catId          идентификатор изменяемой категории
     * @param updCategoryDto данные к измнению
     * @return 200 Данные категории изменены
     * 404 Категория не найдена или недоступна
     * 409 Нарушение целостности данных
     */
    CategoryDto updateCategory(Long catId, NewCategoryDto updCategoryDto);

    /**
     * Удаление категории (с категорией не должно быть связано ни одного события)
     *
     * @param userId идентификатор удаляемой категории
     * @return 204 Категория удалена
     * 404 Категория не найдена или недоступна
     * 409 Существуют события, связанные с категорией
     */
    Boolean deleteCategoryById(long userId);
}
