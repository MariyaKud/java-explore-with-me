package ru.practicum.dto.input.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.dto.ContextStats;
import ru.practicum.dto.output.outshort.LocationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestBase {
    @Size(min = 20, max = 2000)
    private String annotation;
    @Positive
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(pattern = ContextStats.pattern)
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    @Size(min = 3, max = 120)
    private String title;

    public boolean isAnnotation() {
        return annotation != null;
    }

    public boolean isCategory() {
        return category != null;
    }

    public boolean isDescription() {
        return description != null;
    }

    public boolean isPaid() {
        return paid != null;
    }

    public boolean isParticipantLimit() {
        return participantLimit != null;
    }

    public boolean isRequestModeration() {
        return requestModeration != null;
    }

    public boolean isTitle() {
        return title != null;
    }

    public boolean isEventDate() {
        return eventDate != null;
    }

    public boolean isLocation() {
        return location != null;
    }
}
