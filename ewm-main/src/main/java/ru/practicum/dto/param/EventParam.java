package ru.practicum.dto.param;

import lombok.*;
import ru.practicum.model.enummodel.EventSort;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventParam {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private EventSort sort;

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
