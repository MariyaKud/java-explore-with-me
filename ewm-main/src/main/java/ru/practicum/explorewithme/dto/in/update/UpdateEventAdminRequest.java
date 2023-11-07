package ru.practicum.explorewithme.dto.in.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import ru.practicum.explorewithme.dto.out.outshort.LocationDto;
import ru.practicum.explorewithme.model.enummodel.StateActionReview;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
    private String annotation;
    private Integer category;
    private String description;
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionReview stateAction;
    private String title;
}
