package ru.practicum.service.compilation;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.practicum.dto.input.create.NewCompilationDto;
import ru.practicum.dto.output.CompilationDto;
import ru.practicum.dto.output.outshort.EventShortDto;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.service.event.EventMapper;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventMapper eventMapper;

    public Compilation fromDto(NewCompilationDto newCompilationDto, Set<Event> events) {
        return Compilation.builder()
                .events(events)
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }

    public CompilationDto toDto(Compilation compilation) {
        List<EventShortDto> events = eventMapper.mapToEventDto(compilation.getEvents(),
                                                   false, false, new LinkedList<>());

        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(new HashSet<>(events))
                .build();
    }
}
