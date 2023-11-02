package ru.practicum.explorewithme.controller.ewmadmin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.in.create.NewCompilationDto;
import ru.practicum.explorewithme.dto.in.update.UpdateCompilationRequest;
import ru.practicum.explorewithme.dto.out.CompilationDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {

    @PostMapping
    public CompilationDto createCCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Creating new compilation {}", newCompilationDto);
        return null;
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @RequestBody @Valid UpdateCompilationRequest compilationDto) {
        log.info("Patch compilation with id {}, compilation {}", compId, compilationDto);
        return null;
    }

    @DeleteMapping("/{compId}")
    public boolean deleteCompilation(@PathVariable Long compId) {
        log.info("Delete compilation by id {}", compId);
        return true;
    }
}
