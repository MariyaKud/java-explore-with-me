package ru.practicum.explorewithme.dto.in.create;

import lombok.*;
import ru.practicum.explorewithme.dto.out.inner.LocationDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
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
    private LocalDateTime eventDate;
    private LocationDto location;
    @Value("false")
    private Boolean paid;
    @Positive
    @Value("0")
    private Integer participantLimit;
    @Value("true")
    private Boolean requestModeration;
}
