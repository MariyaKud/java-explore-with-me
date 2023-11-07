package ru.practicum.explorewithme.dto.out;

import lombok.*;
import ru.practicum.explorewithme.dto.out.outshort.EventShortDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private Boolean pinned;
    private String title;
    private List<EventShortDto> events;
}
