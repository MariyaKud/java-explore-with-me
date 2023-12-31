package ru.practicum.controller.ewmprivate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.output.ParticipationRequestDto;
import ru.practicum.service.request.PrivateRequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class UserRequestController {
    private final PrivateRequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@Positive @PathVariable Long userId) {
        log.info("Get requests for user by id {}", userId);
        return requestService.getUserRequests(userId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ParticipationRequestDto createUserRequest(@Positive @PathVariable Long userId,
                                                     @RequestParam(name = "eventId") Long eventId) {
        log.info("User by id {} creating new request for event id={}", userId, eventId);
        return requestService.createUserRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelUserRequest(@Positive @PathVariable Long userId,
                                                     @Positive @PathVariable("requestId") Long requestId) {
        log.info("User by id {} cancel request by id={}", userId, requestId);
        return requestService.cancelUserRequest(userId, requestId);
    }
}
