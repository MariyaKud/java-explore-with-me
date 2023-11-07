package ru.practicum.explorewithme.controller.ewmpublic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import ru.practicum.dto.ContextStats;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.out.outshort.EventShortDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam @NotBlank String text,
                                         @RequestParam(name = "categories", defaultValue = "") List<Long> categories,
                                         @RequestParam Boolean paid,
                                         @RequestParam @DateTimeFormat(pattern = ContextStats.pattern) LocalDateTime rangeStart,
                                         @RequestParam @DateTimeFormat(pattern = ContextStats.pattern) LocalDateTime rangeEnd,
                                         @RequestParam Boolean onlyAvailable,
                                         @RequestParam @NotBlank String sort,
                                         @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get events with text {}, categories={}, paid={}, start={}, end={}, onlyAvailable={}, sort={}, from {}, size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        /*
        Обратите внимание:
        это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
        текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
        если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени
        информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
        информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
        В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
         */
        return null;
    }

    @GetMapping("/{id}")
    public EventFullDto findByIdEvent(@PathVariable Long id) {
        log.info("Find event by id {}", id);
        /*
        Обратите внимание:

        событие должно быть опубликовано
        информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
        информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
        В случае, если события с заданным id не найдено, возвращает статус код 404
         */
        return null;
    }
}
