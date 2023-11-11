package ru.practicum.explorewithme.dto.out;

import lombok.*;

import ru.practicum.explorewithme.dto.out.outshort.LocationDto;
import ru.practicum.explorewithme.dto.out.outshort.UserShortDto;
import ru.practicum.explorewithme.model.enummodel.EventState;

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
    private String createdOn;
    private String description;
    private String eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventState state;
    private Integer views;
}
