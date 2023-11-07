package ru.practicum.explorewithme.controller.ewmprivate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.out.ParticipationRequestDto;
import ru.practicum.explorewithme.service.request.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class UserRequestController {
    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        log.info("Get requests for user by id {}", userId);
        return requestService.getUserRequests(userId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ParticipationRequestDto createUserRequest(@PathVariable Long userId,
                                                     @RequestParam(name = "eventId ") Long eventId) {
        log.info("User by id {} creating new request for event id={}", userId, eventId);
        return requestService.createUserRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public Boolean cancelUserRequest(@PathVariable Long userId, @PathVariable("requestId") Long requestId) {
        log.info("User by id {} cancel request by id={}", userId, requestId);
        return requestService.cancelUserRequest(userId, requestId);
    }
}
