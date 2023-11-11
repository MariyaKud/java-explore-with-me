package ru.practicum.explorewithme.dto.param;

import lombok.*;
import ru.practicum.explorewithme.model.enummodel.EventSort;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventParam {
    String text;
    List<Long> categories;
    Boolean paid;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Boolean onlyAvailable;
    EventSort sort;

    public boolean isText() {
        return text != null && !text.isBlank();
    }

    public boolean isCategories() {
        return categories != null && !categories.isEmpty();
    }

    public boolean isPaid() {
        return paid != null;
    }

    public boolean isSort() {
        return sort != null;
    }

    public boolean isOnlyAvailable() {
        return onlyAvailable != null;
    }

    public boolean isRangeStart() {
        return rangeStart != null;
    }

    public boolean isRangeEnd() {
        return rangeEnd != null;
    }
}
