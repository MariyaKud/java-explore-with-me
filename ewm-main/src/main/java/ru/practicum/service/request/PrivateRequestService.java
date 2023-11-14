package ru.practicum.service.request;

import ru.practicum.dto.input.update.EventRequestStatusUpdateRequest;
import ru.practicum.dto.output.EventRequestStatusUpdateResult;
import ru.practicum.dto.output.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {

    /**
     * Добавление от текущего пользователя на участие в событии
     * Нельзя добавить повторный запрос (Ожидается код ошибки 409)
     * Инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
     * Нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
     * Если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
     * Если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в
     * состояние подтвержденного
     *
     * @param userId  id текущего пользователя
     * @param eventId id события
     * @return 201 Заявка создана
     * 400 Запрос составлен некорректно
     * 404 Запрос составлен некорректно
     * 409 Нарушение целостности данных
     */
    ParticipationRequestDto createUserRequest(Long userId, Long eventId);

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     *
     * @param userId id текущего пользователя
     * @return 200 Найдены запросы на участие
     * 400 Запрос составлен некорректно
     * 404 Пользователь не найден
     */
    List<ParticipationRequestDto> getUserRequests(Long userId);

    /**
     * Отмена своего запроса на участие в событии
     *
     * @param userId    id текущего пользователя
     * @param requestId id запроса на участие
     * @return 200 Заявка отменена
     * 404 Запрос не найден или недоступен
     */
    ParticipationRequestDto cancelUserRequest(Long userId, Long requestId);

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     *
     * @param userId  id текущего пользователя
     * @param eventId id события
     * @return 200 Найдены запросы на участие
     * 400 Запрос составлен некорректно
     */
    List<ParticipationRequestDto> findRequestsForUserEventById(Long userId, Long eventId);

    /**
     * Изменение статуса (подтверждение, отмена) заявок на участие в событии текущего пользователя
     * Если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется.
     * Нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
     * Статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409).
     * Если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
     *
     * @param userId         id текущего пользователя
     * @param eventId        id события текущего пользователя
     * @param statusRequests Новый статус для заявок на участие в событии текущего пользователя
     * @return 200 Статус заявок изменён
     * 400 Запрос составлен некорректно
     * 404 Событие не найдено или недоступно
     * 409 Достигнут лимит одобренных заявок
     */
    EventRequestStatusUpdateResult updateStatusRequestsForUserEvent(Long userId, Long eventId,
                                                                    EventRequestStatusUpdateRequest statusRequests);
}
