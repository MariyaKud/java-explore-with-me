package ru.practicum.controller.ewmprivate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.input.create.NewEventDto;
import ru.practicum.dto.input.update.EventRequestStatusUpdateRequest;
import ru.practicum.dto.input.update.UpdateEventUserRequest;
import ru.practicum.dto.output.EventFullDto;
import ru.practicum.dto.output.EventRequestStatusUpdateResult;
import ru.practicum.dto.output.ParticipationRequestDto;
import ru.practicum.dto.output.outshort.EventShortDto;
import ru.practicum.service.event.PrivateEventService;
import ru.practicum.service.request.PrivateRequestService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
public class UserEventController {
    private final PrivateEventService eventService;

    private final PrivateRequestService requestService;

    @GetMapping
    public List<EventShortDto> getUserEvents(@Positive @PathVariable Long userId,
                                             @RequestParam(name = "isNeedModeration", required = false) boolean isNeedModeration,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get events for user by id {} filter for need moderation = {}, from={}, size={}",
                userId, isNeedModeration, from, size);
        return eventService.getUserEvents(userId, isNeedModeration, from, size);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto createUserEvent(@Positive @PathVariable Long userId,
                                        @RequestBody @Valid NewEventDto newEventDto) {
        log.info("User by id {} creating new event {}", userId, newEventDto);
        return eventService.createUserEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto findUserEventById(@Positive @PathVariable Long userId,
                                          @Positive @PathVariable Long eventId) {
        log.info("User by id {} find event id={}", userId, eventId);
        return eventService.findUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateUserEvent(@Positive @PathVariable Long userId,
                                        @Positive @PathVariable Long eventId,
                                        @RequestBody @Valid UpdateEventUserRequest eventDto) {
        log.info("User by id {} patch event by id {}, event {}", userId, eventId, eventDto);
        return eventService.updateUserEvent(userId, eventId, eventDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> findRequestsForUserEventById(@Positive @PathVariable Long userId,
                                                                      @Positive @PathVariable Long eventId) {
        log.info("User by id {} find event id={}", userId, eventId);
        return requestService.findRequestsForUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateStatusRequestsForUserEvent(@Positive @PathVariable Long userId,
                                                                           @Positive @PathVariable Long eventId,
                                                                           @RequestBody @Valid EventRequestStatusUpdateRequest statusRequests) {
        log.info("User by id {} patch request status for event by id {}, status={}", userId, eventId, statusRequests);
        return requestService.updateStatusRequestsForUserEvent(userId, eventId, statusRequests);
    }
}
