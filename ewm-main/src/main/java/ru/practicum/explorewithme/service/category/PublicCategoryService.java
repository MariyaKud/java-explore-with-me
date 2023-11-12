package ru.practicum.explorewithme.service.category;

import ru.practicum.explorewithme.dto.output.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    /**
     * Получение категорий
     *
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора
     * @param size количество категорий в наборе
     * @return 200 Категории найдены
     * 400 Запрос составлен некорректно
     */
    List<CategoryDto> getCategories(Integer from, Integer size);

    /**
     * Получение информации о категории по идентификатору
     *
     * @param catId идентификатор категории
     * @return 200 Категория найдена
     * 400 Запрос составлен некорректно
     * 404 Категория не найдена или недоступна
     */
    CategoryDto findByIdCategory(Long catId);
}
