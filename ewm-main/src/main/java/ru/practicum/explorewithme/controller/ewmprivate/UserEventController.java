package ru.practicum.explorewithme.controller.ewmprivate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.in.create.NewEventDto;
import ru.practicum.explorewithme.dto.in.update.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.dto.in.update.UpdateEventUserRequest;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.out.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.dto.out.EventShortDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class UserEventController {
    @GetMapping
    public List<EventShortDto> getUserEvents(@PathVariable Long userId,
                                             @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get events for user by id {}, from={}, size={}", userId, from, size);
        return null;
    }

    @PostMapping
    public EventFullDto createUserEvent(@PathVariable Long userId,
                                         @RequestBody @Valid NewEventDto newEventDto) {
        log.info("User by id {} creating new event {}", userId, newEventDto);
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto findUserEventById(@PathVariable Long userId,
                                          @PathVariable Long eventId) {
        log.info("User by id {} find event id={}", userId, eventId);
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateUserEvent(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @RequestBody @Valid UpdateEventUserRequest eventDto) {
        log.info("User by id {} patch event by id {}, event {}", userId, eventId, eventDto);
        return null;
    }

    @GetMapping("/{eventId}/requests")
    public EventFullDto findRequestsForUserEventById(@PathVariable Long userId,
                                                     @PathVariable Long eventId) {
        log.info("User by id {} find event id={}", userId, eventId);
        return null;
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateStatusRequestsForUserEvent(@PathVariable Long userId,
                                                                           @PathVariable Long eventId,
                                                                           @RequestBody @Valid EventRequestStatusUpdateRequest statusRequests) {
        log.info("User by id {} patch request status for event by id {}, status={}", userId, eventId, statusRequests);
        return null;
    }
}
