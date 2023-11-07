package ru.practicum.explorewithme.service.event;

import ru.practicum.explorewithme.dto.param.AdminEventParam;
import ru.practicum.explorewithme.dto.in.create.NewEventDto;
import ru.practicum.explorewithme.dto.in.update.UpdateEventAdminRequest;
import ru.practicum.explorewithme.dto.in.update.UpdateEventUserRequest;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.out.outshort.EventShortDto;

import java.util.List;

public interface EventService {

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
    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    /**
     * Получение событий, добавленных текущим пользователем
     *
     * @param userId id текущего пользователя
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size   количество элементов в наборе
     * @return 200 События найдены
     * 400 Запрос составлен некорректно
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

    /**
     * Редактирование данных событий и его статуса администратором. Валидация данных не требуется.
     * Дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
     * Событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409)
     * Событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)
     *
     * @param eventId  id события
     * @param eventDto Данные для изменения информации о событии
     * @return 200 Событие отредактировано
     * 404 Событие не найдено или недоступно
     * 409 Событие не удовлетворяет правилам редактирования
     */
    EventFullDto updateAdminEvent(Long eventId, UpdateEventAdminRequest eventDto);

    /**
     * Поиск событий. Возвращает полную информацию обо всех событиях подходящих под переданные условия
     *
     * @param eventParam список условий
     * @param from       количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size       количество элементов в наборе
     * @return 200 События найдены
     * 400 запрос составлен некорректно
     */
    List<EventShortDto> getEvents(AdminEventParam eventParam, Integer from, Integer size);
}
