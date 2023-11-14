package ru.practicum.dto.param;

import lombok.*;
import ru.practicum.model.enummodel.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminEventParam {
    private List<Long> users;
    private List<EventState> states;
    private List<Long> categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;

    public boolean isUsers() {
        return users != null && !users.isEmpty();
    }

    public boolean isStates() {
        return states != null && !states.isEmpty();
    }

    public boolean isCategories() {
        return categories != null && !categories.isEmpty();
    }

    public boolean isRangeStart() {
        return rangeStart != null;
    }

    public boolean isRangeEnd() {
        return rangeEnd != null;
    }
}
