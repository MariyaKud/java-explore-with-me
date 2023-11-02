package ru.practicum.explorewithme.controller.ewmadmin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import ru.practicum.dto.ContextStats;
import ru.practicum.explorewithme.dto.in.update.UpdateEventAdminRequest;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.out.EventShortDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "users", defaultValue = "") List<Long> users,
                                        @RequestParam(name = "states", defaultValue = "") List<String> states,
                                        @RequestParam(name = "categories", defaultValue = "") List<Long> categories,
                                        @RequestParam @DateTimeFormat(pattern = ContextStats.pattern) LocalDateTime rangeStart,
                                        @RequestParam @DateTimeFormat(pattern = ContextStats.pattern) LocalDateTime rangeEnd,
                                        @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get events for users ids {} in states={} and categories={}, start={}, end={}, from={}, size={}",
                   users, states, categories, rangeStart, rangeEnd, from, size);
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long eventId,
                                    @RequestBody @Valid UpdateEventAdminRequest eventDto) {
        log.info("Patch event with id {}, event {}", eventId, eventDto);
        return null;
    }
}
