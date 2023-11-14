package ru.practicum.service.event;

import ru.practicum.dto.input.create.NewEventDto;
import ru.practicum.dto.input.update.UpdateEventUserRequest;
import ru.practicum.dto.output.EventFullDto;
import ru.practicum.dto.output.outshort.EventShortDto;

import java.util.List;

public interface PrivateEventService {

    /**
     * Добавление нового события (дата и время на которые намечено событие не может быть раньше,
     * чем через два часа от текущего момента)
     *
     * @param userId      id текущего пользователя
     * @param newEventDto данные добавляемого события
     * @return 201 Событие добавлено
     * 400 Запрос составлен некорректно
     * 409 Событие не удовлетворяет правилам создания (не корректная дата события)
     */
    EventFullDto createUserEvent(Long userId, NewEventDto newEventDto);

    /**
     * Получение событий, добавленных текущим пользователем
     *
     * @param userId id текущего пользователя
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size   количество элементов в наборе
     * @return 200 События найдены
     * 400 Запрос составлен некорректно
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size);

    /**
     * Получение полной информации о событии добавленном текущим пользователем
     *
     * @param userId  id текущего пользователя
     * @param eventId id события
     * @return 200 Событие айдено
     * 400 Запрос составлен некорректно
     * 404 Событие не найдено или недоступно
     */
    EventFullDto findUserEventById(Long userId, Long eventId);

    /**
     * Изменение события добавленного текущим пользователем
     * изменить можно только отмененные события или события в состоянии ожидания модерации (Ожидается код ошибки 409)
     * дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     * (Ожидается код ошибки 409)
     *
     * @param userId   id текущего пользователя
     * @param eventId  id события
     * @param eventDto Новые данные события
     * @return 200 Событие обновлено
     * 400 Запрос составлен некорректно
     * 404 Событие не найдено или недоступно
     * 409 Событие не удовлетворяет правилам редактирования
     */
    EventFullDto updateUserEvent(Long userId, Long eventId, UpdateEventUserRequest eventDto);
}
