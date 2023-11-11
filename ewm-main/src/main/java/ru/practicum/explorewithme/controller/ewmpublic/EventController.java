package ru.practicum.explorewithme.controller.ewmpublic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.web.bind.annotation.*;

import ru.practicum.explorewithme.service.client.StatsAgent;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.out.outshort.EventShortDto;
import ru.practicum.explorewithme.dto.param.EventParam;
import ru.practicum.explorewithme.model.enummodel.EventSort;
import ru.practicum.explorewithme.service.event.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.dto.ContextStats.formatter;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final PublicEventService eventService;

    private final StatsAgent statsAgent;

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text", defaultValue = "") String text,
                                         @RequestParam(name = "categories", defaultValue = "") List<Long> categories,
                                         @RequestParam(name = "paid", defaultValue = "") Boolean paid,
                                         @RequestParam(name = "rangeStart", defaultValue = "") String rangeStart,
                                         @RequestParam(name = "rangeEnd", defaultValue = "") String rangeEnd,
                                         @RequestParam(name = "onlyAvailable", defaultValue = "") Boolean onlyAvailable,
                                         @RequestParam(name = "sort", defaultValue = "") String sort,
                                         @RequestParam(name = "from", defaultValue = "0") Integer from,
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

        if (!sort.isEmpty()) {
            param.setSort(EventSort.valueOf(sort));
        }
        if (!rangeStart.isEmpty()) {
            param.setRangeStart(LocalDateTime.parse(rangeStart, formatter));
        }
        if (!rangeEnd.isEmpty()) {
            param.setRangeEnd(LocalDateTime.parse(rangeEnd, formatter));
        }

        if (!rangeStart.isEmpty() && !rangeEnd.isEmpty()
                && param.getRangeEnd().isBefore(param.getRangeStart())) {
            throw new IllegalArgumentException("Date start is after then end.");
        }

        List<EventShortDto> events = eventService.getEvents(param, from, size);

        for (EventShortDto event : events) {
            statsAgent.recordStats(request.getRemoteAddr(),
                    String.join("/", "/events", Long.toString(event.getId())));
        }

        statsAgent.recordStats(request.getRemoteAddr(), "/events");

        return events;
    }

    @GetMapping("/{id}")
    public EventFullDto findByIdEvent(@PathVariable Long id, HttpServletRequest request) {
        log.info("Find event by id {}", id);
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());

        EventFullDto eventFullDto = eventService.findEventById(id);

        statsAgent.recordStats(request.getRemoteAddr(), request.getRequestURI());

        return eventFullDto;
    }
}


