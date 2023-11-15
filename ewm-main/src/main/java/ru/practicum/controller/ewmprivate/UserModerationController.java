package ru.practicum.controller.ewmprivate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.output.AdminCommentDto;
import ru.practicum.service.event.PrivateModerationService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/moderation")
@RequiredArgsConstructor
@Validated
public class UserModerationController {
    private final PrivateModerationService moderationService;

    @GetMapping
    public List<AdminCommentDto> getComments(@Positive @PathVariable Long userId,
                                             @RequestParam(name = "isPending", required = false) boolean isPending,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get comments for events by user id {} only pending = {}, from={}, size={}",
                userId, isPending, from, size);

        return moderationService.getUserComments(userId, isPending, from, size);
    }
}
