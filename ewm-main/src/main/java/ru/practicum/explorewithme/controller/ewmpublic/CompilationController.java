package ru.practicum.explorewithme.controller.ewmpublic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.output.CompilationDto;
import ru.practicum.explorewithme.service.compilation.PublicCompilationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final PublicCompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", defaultValue = "") Boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get compilations of events pinned ({}), from={}, size={}\"", pinned, from, size);
        if (pinned == null) {
            return compilationService.getCompilations(from, size);
        } else {
            return compilationService.getPinnedCompilations(pinned, from, size);
        }
    }

    @GetMapping("/{compId}")
    public CompilationDto findByIdCompilation(@PathVariable Long compId) {
        log.info("Find compilation by id {}", compId);
        return compilationService.findCompilationById(compId);
    }
}
