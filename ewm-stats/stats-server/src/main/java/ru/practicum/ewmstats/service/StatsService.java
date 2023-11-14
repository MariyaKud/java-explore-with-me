package ru.practicum.ewmstats.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    /**
     * Получение статистики по посещениям
     *
     * @param startData Дата и время начала диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
     * @param endData   Дата и время конца диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
     * @param uris      Список uri для которых нужно выгрузить статистику
     * @param unique    Нужно ли учитывать только уникальные посещения (только с уникальным ip)
     * @return 200 Статистика собрана
     */
    List<ViewStatsDto> getStats(LocalDateTime startData, LocalDateTime endData, List<String> uris, Boolean unique);

    /**
     * Сохранение информации о том, что к эндпоинту был запрос
     * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
     * Название сервиса, uri и ip пользователя указаны в теле запроса.
     *
     * @param endpointHitDto данные запроса
     * @return 201 Информация сохранена
     */
    EndpointHitDto createHit(EndpointHitDto endpointHitDto);
}
