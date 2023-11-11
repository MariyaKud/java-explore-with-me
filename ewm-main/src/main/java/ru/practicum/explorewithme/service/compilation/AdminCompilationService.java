package ru.practicum.explorewithme.service.compilation;

import ru.practicum.explorewithme.dto.in.create.NewCompilationDto;
import ru.practicum.explorewithme.dto.in.update.UpdateCompilationRequest;
import ru.practicum.explorewithme.dto.out.CompilationDto;

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
