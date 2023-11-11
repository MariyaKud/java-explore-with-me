package ru.practicum.explorewithme.controller.ewmprivate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.in.create.NewEventDto;
import ru.practicum.explorewithme.dto.in.update.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.dto.in.update.UpdateEventUserRequest;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.out.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.dto.out.ParticipationRequestDto;
import ru.practicum.explorewithme.dto.out.outshort.EventShortDto;
import ru.practicum.explorewithme.service.event.PrivateEventService;
import ru.practicum.explorewithme.service.request.PrivateRequestService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class UserEventController {
    private final PrivateEventService eventService;

    private final PrivateRequestService requestService;

    @GetMapping
    public List<EventShortDto> getUserEvents(@PathVariable Long userId,
                                            @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get events for user by id {}, from={}, size={}", userId, from, size);
        return eventService.getUserEvents(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto createUserEvent(@PathVariable Long userId,
                                         @RequestBody @Valid NewEventDto newEventDto) {
        log.info("User by id {} creating new event {}", userId, newEventDto);
        return eventService.createUserEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto findUserEventById(@PathVariable Long userId,
                                          @PathVariable Long eventId) {
        log.info("User by id {} find event id={}", userId, eventId);
        return eventService.findUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateUserEvent(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @RequestBody @Valid UpdateEventUserRequest eventDto) {
        log.info("User by id {} patch event by id {}, event {}", userId, eventId, eventDto);
        return eventService.updateUserEvent(userId, eventId, eventDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> findRequestsForUserEventById(@PathVariable Long userId,
                                                                      @PathVariable Long eventId) {
        log.info("User by id {} find event id={}", userId, eventId);
        return requestService.findRequestsForUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateStatusRequestsForUserEvent(@PathVariable Long userId,
                                                                           @PathVariable Long eventId,
                                          @RequestBody @Valid EventRequestStatusUpdateRequest statusRequests) {
        log.info("User by id {} patch request status for event by id {}, status={}", userId, eventId, statusRequests);
        return requestService.updateStatusRequestsForUserEvent(userId, eventId, statusRequests);
    }
}
