package ru.practicum.explorewithme.service.event;

import ru.practicum.explorewithme.dto.in.update.UpdateEventAdminRequest;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.param.AdminEventParam;

import java.util.List;

public interface AdminEventService {
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
     * Поиск событий. Возвращает полную информацию обо всех событиях подходящих под переданные условия.
     *
     * @param eventParam список условий:
     *                   <b>users</b> список id пользователей, чьи события нужно найти
     *                   <b>states</b> список состояний в которых находятся искомые события
     *                   <b>categories</b> список id категорий в которых будет вестись поиск
     *                   <b>rangeStart</b> дата и время не раньше которых должно произойти событие
     *                   <b>rangeEnd</b> дата и время не позже которых должно произойти событие
     * @param from       количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size       количество элементов в наборе
     * @return 200 События найдены
     * 400 запрос составлен некорректно
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    List<EventFullDto> getAdminEvents(AdminEventParam eventParam, Integer from, Integer size);
}
