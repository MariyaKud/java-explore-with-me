package ru.practicum.explorewithme.dto.in.create;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import ru.practicum.dto.ContextStats;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import ru.practicum.explorewithme.model.Location;
import ru.practicum.explorewithme.validation.DateAfterTwoHourFromNow;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@DateAfterTwoHourFromNow
public class NewEventDto {
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    @Positive
    private Long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(pattern = ContextStats.pattern)
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid = false;
    @PositiveOrZero
    private Integer participantLimit = 0;
    private Boolean requestModeration = true;
}
