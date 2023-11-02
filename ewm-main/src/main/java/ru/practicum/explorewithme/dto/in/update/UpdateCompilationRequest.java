package ru.practicum.explorewithme.dto.in.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateCompilationRequest {
    private Set<Long> events;
    private boolean pinned;
    @Size(min = 1, max = 50)
    private String title;
}
