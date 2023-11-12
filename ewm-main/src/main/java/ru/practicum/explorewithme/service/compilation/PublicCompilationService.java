package ru.practicum.explorewithme.service.compilation;

import ru.practicum.explorewithme.dto.output.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    /**
     * Получение подборки событий
     *
     * @param pinned искать только закрепленные/не закрепленные подборки
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size   количество элементов в наборе
     * @return 200 Найдены подборки событий
     * 400 Запрос составлен некорректно
     */
    List<CompilationDto> getPinnedCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * Получение подборки событий
     *
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size количество элементов в наборе
     * @return 200 Найдены подборки событий
     * 400 Запрос составлен некорректно
     */
    List<CompilationDto> getCompilations(Integer from, Integer size);

    /**
     * Получение подборки событий по идентификатору
     *
     * @param compId идентфиикатор подборки
     * @return 200 Подборка событий найдена
     * 400 Запрос составлен некорректно
     * 404 Подборка не найдена или недоступна
     */
    CompilationDto findCompilationById(Long compId);
}
