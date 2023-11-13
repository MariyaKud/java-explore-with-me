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
    List<Long> users;
    List<EventState> states;
    List<Long> categories;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;

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
