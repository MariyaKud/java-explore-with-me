package ru.practicum.explorewithme.dto.out.inner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LocationDto {
    private Float lat;
    private Float lon;
}
