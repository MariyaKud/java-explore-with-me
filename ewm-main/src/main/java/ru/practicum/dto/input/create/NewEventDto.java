package ru.practicum.dto.input.create;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import ru.practicum.dto.ContextStats;

import javax.validation.Valid;
import javax.validation.constraints.*;

import ru.practicum.dto.output.outshort.LocationDto;
import ru.practicum.validation.DateAfterTwoHourFromNow;

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
    @NotNull
    private Long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(pattern = ContextStats.pattern)
    private LocalDateTime eventDate;
    @Valid
    @NotNull
    private LocationDto location;
    private boolean paid;
    @PositiveOrZero
    private int participantLimit;
    private boolean requestModeration = true;
}
