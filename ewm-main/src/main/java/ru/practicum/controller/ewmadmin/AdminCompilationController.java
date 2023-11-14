package ru.practicum.controller.ewmadmin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.input.create.NewCompilationDto;
import ru.practicum.dto.input.update.UpdateCompilationRequest;
import ru.practicum.dto.output.CompilationDto;
import ru.practicum.service.compilation.AdminCompilationService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Validated
public class AdminCompilationController {
    private final AdminCompilationService compilationService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Creating new compilation {}", newCompilationDto);
        return compilationService.createCompilation(newCompilationDto);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PositiveOrZero @PathVariable Long compId,
                                            @RequestBody @Valid UpdateCompilationRequest compilationDto) {
        log.info("Patch compilation with id {}, compilation {}", compId, compilationDto);
        return compilationService.updateCompilationById(compId, compilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Boolean deleteCompilation(@PositiveOrZero @PathVariable Long compId) {
        log.info("Delete compilation by id {}", compId);
        return compilationService.deleteCompilationById(compId);
    }
}
