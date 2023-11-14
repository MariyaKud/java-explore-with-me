package ru.practicum.dto.output.outshort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LocationDto {
    @NotNull
    private Float lat;
    @NotNull
    private Float lon;
}
