package ru.practicum.controller.ewmadmin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.practicum.dto.input.update.UpdateEventAdminRequest;
import ru.practicum.dto.output.EventFullDto;
import ru.practicum.dto.param.AdminEventParam;
import ru.practicum.model.enummodel.EventState;
import ru.practicum.service.event.AdminEventService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.dto.ContextStats.formatter;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Validated
public class AdminEventController {
    private final AdminEventService eventService;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                        @RequestParam(name = "states", required = false) List<String> states,
                                        @RequestParam(name = "categories", required = false) List<Long> categories,
                                        @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get events for users ids {} in states={} and categories={}, start={}, end={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);

        AdminEventParam param = AdminEventParam.builder()
                .users(users)
                .categories(categories)
                .build();

        if (states != null) {
            param.setStates(states.stream().map(EventState::valueOf).collect(Collectors.toList()));
        }
        if (rangeStart != null) {
            param.setRangeStart(LocalDateTime.parse(rangeStart, formatter));
        } else {
            param.setRangeStart(LocalDateTime.now());
        }
        if (rangeEnd != null) {
            param.setRangeEnd(LocalDateTime.parse(rangeEnd, formatter));
        }

        return eventService.getAdminEvents(param, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@Positive @PathVariable Long eventId,
                                    @RequestBody @Valid UpdateEventAdminRequest eventDto) {
        log.info("Patch event with id {}, event {}", eventId, eventDto);
        return eventService.updateAdminEvent(eventId, eventDto);
    }
}
