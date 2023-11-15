package ru.practicum.service.event;

import ru.practicum.dto.output.AdminCommentDto;

import java.util.List;

public interface AdminModerationService {

    /**
     * Администратор добавляет комментарий к своему отказу опубликовать событие.
     *
     * @param eventId идентификатор события
     * @param text    текст пояснения
     * @return 201 Комментарий добавлен
     * 400 Запрос составлен не корректно
     * 409 Событие не удовлетворяет правилам создания комментария (статус события должен быть отмененное)
     */
    AdminCommentDto createComment(Long eventId, String text);

    /**
     * Получение комментариев модератора по отмененным событиям
     *
     * @param eventIds список идентификаторов событий
     * @param isFixed  интересуют только исправленные
     * @param from     количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size     количество элементов в наборе
     * @return 200 Комментарии найдены
     * 400 Запрос составлен некорректно
     * В случае, если по заданным фильтрам не найдено ни одного комментария, возвращает пустой список
     */
    List<AdminCommentDto> getComments(List<Long> eventIds, boolean isFixed, Integer from, Integer size);
}
