package ru.practicum.client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatsAgent {

    /**
     * Сохранить в сервисе статистики информацию о том, что к событию был запрос
     * @param ip адрес пользователя, осуществившего запрос
     * @param uri  uri для которых нужно записать статистику
     */
    void recordStats(String ip, String uri);

    /**
     * Получить статистики по посещению события
     * @param id иеднтификатор событияб для которого надо прочитать статистику обращений
     * @param start Дата и время начала диапазона за который нужно выгрузить статистику
     * @param end Дата и время конца диапазона за который нужно выгрузить статистику
     * @return количество посещений
     */
    int getStatsByEventId(Long id, LocalDateTime start, LocalDateTime end);

    /**
     * Получить статистики по посещению событий
     * @param ids иеднтификаторы событияб для которого надо прочитать статистику обращений
     * @param start Дата и время начала диапазона за который нужно выгрузить статистику
     * @param end Дата и время конца диапазона за который нужно выгрузить статистику
     * @return количество посещений по каждому переданному идентификатору событий
     */
    Map<Long, Integer> getStatsByEventIds(List<Long> ids, LocalDateTime start, LocalDateTime end);
}
