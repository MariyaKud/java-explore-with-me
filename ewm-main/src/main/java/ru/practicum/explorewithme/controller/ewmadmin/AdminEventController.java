package ru.practicum.explorewithme.controller.ewmadmin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.web.bind.annotation.*;

import ru.practicum.explorewithme.dto.input.update.UpdateEventAdminRequest;
import ru.practicum.explorewithme.dto.output.EventFullDto;
import ru.practicum.explorewithme.dto.param.AdminEventParam;
import ru.practicum.explorewithme.model.enummodel.EventState;
import ru.practicum.explorewithme.service.event.AdminEventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.dto.ContextStats.formatter;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final AdminEventService eventService;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(name = "users", defaultValue = "") List<Long> users,
                                       @RequestParam(name = "states", defaultValue = "") List<String> states,
                                       @RequestParam(name = "categories", defaultValue = "") List<Long> categories,
                                       @RequestParam(name = "rangeStart", defaultValue = "") String rangeStart,
                                       @RequestParam(name = "rangeEnd", defaultValue = "") String rangeEnd,
                                       @RequestParam(name = "from", defaultValue = "0") Integer from,
                                       @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get events for users ids {} in states={} and categories={}, start={}, end={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);

        AdminEventParam param = AdminEventParam.builder()
                .users(users)
                .categories(categories)
                .build();

        if (!states.isEmpty()) {
            param.setStates(states.stream().map(EventState::valueOf).collect(Collectors.toList()));
        }
        if (!rangeStart.isEmpty()) {
            param.setRangeStart(LocalDateTime.parse(rangeStart, formatter));
        } else {
            param.setRangeStart(LocalDateTime.now());
        }
        if (!rangeEnd.isEmpty()) {
            param.setRangeEnd(LocalDateTime.parse(rangeEnd, formatter));
        }

        return eventService.getAdminEvents(param, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long eventId,
                                    @RequestBody @Valid UpdateEventAdminRequest eventDto) {
        log.info("Patch event with id {}, event {}", eventId, eventDto);
        return eventService.updateAdminEvent(eventId, eventDto);
    }
}
