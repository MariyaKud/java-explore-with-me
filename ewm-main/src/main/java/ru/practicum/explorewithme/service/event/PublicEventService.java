package ru.practicum.explorewithme.service.event;

import ru.practicum.explorewithme.dto.output.EventFullDto;
import ru.practicum.explorewithme.dto.output.outshort.EventShortDto;
import ru.practicum.explorewithme.dto.param.EventParam;

import java.util.List;

public interface PublicEventService {

    /**
     * Поиск событий с фильтром
     * Это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * Текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * Если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события,
     * которые произойдут позже текущей даты и времени.
     * Информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие.
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
     *
     * @param eventParam фильтры поиска.
     *                   <b>text</b> текст для поиска в содержимом аннотации и подробном описании события;
     *                   <b>categories</b> список идентификаторов категорий в которых будет вестись поиск;
     *                   <b>paid</b> поиск только платных/бесплатных событий;
     *                   <b>rangeStart</b> дата и время не раньше которых должно произойти событие;
     *                   <b>rangeEnd</b> дата и время не позже которых должно произойти событие;
     *                   <b>onlyAvailable</b> только события у которых не исчерпан лимит запросов на участие;
     *                   <b>sort</b> Вариант сортировки: по дате события или по количеству просмотров;
     * @param from       количество событий, которые нужно пропустить для формирования текущего набора
     * @param size       количество событий в наборе
     * @return 200 События найдены
     * 400 Запрос составлен некорректно
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    List<EventShortDto> getEvents(EventParam eventParam, Integer from, Integer size);

    /**
     * Получение подробной информации по событию по его идентификатору.
     * Событие должно быть опубликовано.
     * Информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
     * Информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
     *
     * @param eventId идентификатор события
     * @return 200 Событие найдено
     * 400 Запрос составлен некорректно
     * 404 Событие не найдено или недоступно
     */
    EventFullDto findEventById(Long eventId);
}