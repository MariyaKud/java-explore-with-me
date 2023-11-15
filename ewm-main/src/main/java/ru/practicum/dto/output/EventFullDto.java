package ru.practicum.dto.output;

import lombok.*;

import ru.practicum.dto.output.outshort.LocationDto;
import ru.practicum.dto.output.outshort.UserShortDto;
import ru.practicum.model.enummodel.EventState;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<AdminCommentDto> adminComments;
}
