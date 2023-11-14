package ru.practicum.dto.input.update;

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
    private Boolean pinned;
    @Size(min = 1, max = 50)
    private String title;

    public boolean isSomeChange() {
        return isEvents() || isPinned() || isTittle();
    }

    public boolean isEvents() {
        return events != null && !events.isEmpty();
    }

    public boolean isTittle() {
        return title != null && !title.isEmpty();
    }

    public boolean isPinned() {
        return pinned != null;
    }
}
