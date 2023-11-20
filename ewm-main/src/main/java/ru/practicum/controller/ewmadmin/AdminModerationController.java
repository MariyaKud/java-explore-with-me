package ru.practicum.controller.ewmadmin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.practicum.dto.output.AdminCommentDto;
import ru.practicum.service.event.AdminModerationService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/moderation")
@RequiredArgsConstructor
@Validated
public class AdminModerationController {
    private final AdminModerationService moderationService;

    @PostMapping
    public AdminCommentDto createComment(@RequestParam(name = "eventId") Long eventId,
                                         @NotBlank @RequestParam(name = "text") String text) {
        log.info("Admin creating new comment {} for event id = {}", text, eventId);

        return moderationService.createComment(eventId, text);
    }

    @GetMapping
    public List<AdminCommentDto> getComments(@RequestParam(name = "eventIds", defaultValue = "") List<Long> eventIds,
                                             @RequestParam(name = "isFixed", required = false) boolean isFixed,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get comments for events by ids {} only fixed={}, from={}, size={}",
                eventIds, isFixed, from, size);
        return moderationService.getComments(eventIds, isFixed, from, size);
    }
}
