package ru.practicum.explorewithme.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CategoryDto {
    private Long id;
    private String name;
}
