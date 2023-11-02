package ru.practicum.explorewithme.controller.ewmprivate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.out.ParticipationRequestDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class UserRequestController {
    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        log.info("Get requests for user by id {}", userId);
        return null;
    }

    @PostMapping
    public ParticipationRequestDto createUserRequest(@PathVariable Long userId,
                                                     @RequestParam(name = "eventId ") Long eventId) {
        log.info("User by id {} creating new request for event id={}", userId, eventId);
        return null;
    }

    @PatchMapping("/{requestId}/cancel")
    public boolean cancelUserRequest(@PathVariable Long userId, @PathVariable("requestId") Long requestId) {
        log.info("User by id {} cancel request by id={}", userId, requestId);
        return true;
    }
}
