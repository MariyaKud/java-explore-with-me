package ru.practicum.controller.ewmpublic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.practicum.client.StatsAgent;
import ru.practicum.dto.output.EventFullDto;
import ru.practicum.dto.output.outshort.EventShortDto;
import ru.practicum.dto.param.EventParam;
import ru.practicum.model.enummodel.EventSort;
import ru.practicum.service.event.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.dto.ContextStats.formatter;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class EventController {
    private final PublicEventService eventService;

    private final StatsAgent statsAgent;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                         @RequestParam(name = "categories", required = false) List<Long> categories,
                                         @RequestParam(name = "paid", required = false) Boolean paid,
                                         @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                         @RequestParam(name = "onlyAvailable", required = false) Boolean onlyAvailable,
                                         @RequestParam(name = "sort", required = false) String sort,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                         HttpServletRequest request) {
        log.info("Get events with text {}, categories={}, paid={}, start={}, end={}, onlyAvailable={}, sort={}, " +
                "from {}, size={}", text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());

        EventParam param = EventParam.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .onlyAvailable(onlyAvailable)
                .build();

        if (sort != null) {
            param.setSort(EventSort.valueOf(sort));
        }
        if (rangeStart != null) {
            param.setRangeStart(LocalDateTime.parse(rangeStart, formatter));
        }
        if (rangeEnd != null) {
            param.setRangeEnd(LocalDateTime.parse(rangeEnd, formatter));
        }

        if (rangeStart != null && rangeEnd != null
                && param.getRangeEnd().isBefore(param.getRangeStart())) {
            throw new IllegalArgumentException("Date start is after then end.");
        }

        List<EventShortDto> events = eventService.getEvents(param, from, size);

        statsAgent.recordStats(request.getRemoteAddr(), "/events");

        return events;
    }

    @GetMapping("/{id}")
    public EventFullDto findByIdEvent(@Positive @PathVariable Long id, HttpServletRequest request) {
        log.info("Find event by id {}", id);
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());

        EventFullDto eventFullDto = eventService.findEventById(id);

        statsAgent.recordStats(request.getRemoteAddr(), request.getRequestURI());

        return eventFullDto;
    }
}


