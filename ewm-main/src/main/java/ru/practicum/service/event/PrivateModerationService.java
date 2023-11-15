package ru.practicum.service.event;

import ru.practicum.dto.output.AdminCommentDto;

import java.util.List;

public interface PrivateModerationService {

    /**
     * Получение комментариев модератора по отмененным событиям ntreotuj gjkmpjdfntkz
     *
     * @param userId id текущего пользователя
     * @param isPending интересуют не отработанные комментарии
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size   количество элементов в наборе
     * @return 200 Комментарии найдены
     * 400 Запрос составлен некорректно
     * В случае, если по заданным фильтрам не найдено ни одного комментария, возвращает пустой список
     */
    List<AdminCommentDto> getUserComments(Long userId, boolean isPending, Integer from, Integer size);
}
