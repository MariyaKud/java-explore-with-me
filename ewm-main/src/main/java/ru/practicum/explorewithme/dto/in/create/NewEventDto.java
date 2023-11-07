package ru.practicum.explorewithme.dto.in.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.dto.ContextStats;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;
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
    @Positive
    private Long id;
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
    @Value("false")
    private Boolean paid;
    @Positive
    @Value("0")
    private Integer participantLimit;
    @Value("true")
    private Boolean requestModeration;
}
