package ru.practicum.explorewithme.dto.out;

import lombok.*;
import ru.practicum.explorewithme.dto.out.inner.LocationDto;
import ru.practicum.explorewithme.dto.out.inner.UserShortDto;
import ru.practicum.explorewithme.dto.roster.StatusEventRequest;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class EventFullDto {
    private Long id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private StatusEventRequest state;
    private Integer views;
}
