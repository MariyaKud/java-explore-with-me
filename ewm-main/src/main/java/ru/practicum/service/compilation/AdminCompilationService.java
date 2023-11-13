package ru.practicum.service.compilation;

import ru.practicum.dto.input.create.NewCompilationDto;
import ru.practicum.dto.input.update.UpdateCompilationRequest;
import ru.practicum.dto.output.CompilationDto;

public interface AdminCompilationService {

    /**
     * Добавление новой подборки (подборка может не содержать события)
     *
     * @param newCompilationDto данные новой подборки
     * @return 201 данные новой подборки
     * 400 Запрос составлен некорректно
     * 409 Запрос составлен некорректно
     */
    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    /**
     * Обновить информацию о подборке
     *
     * @param compId         id подборки
     * @param compilationDto данные для обновления подборки
     * @return 200 данные для обновления подборки
     * 404 Подборка не найдена или недоступна
     */
    CompilationDto updateCompilationById(Long compId, UpdateCompilationRequest compilationDto);

    /**
     * Удаление подборки
     *
     * @param compId id подборки
     * @return 204 Подборка удалена
     * 404 Подборка не найдена или недоступна
     */
    Boolean deleteCompilationById(Long compId);
}
