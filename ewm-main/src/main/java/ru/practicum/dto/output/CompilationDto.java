package ru.practicum.dto.output;

import lombok.*;
import ru.practicum.dto.output.outshort.EventShortDto;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {
    private Long id;
    private Boolean pinned;
    private String title;
    private Set<EventShortDto> events;
}
