package ru.practicum.dto.input.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class NewUserRequestDto {
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
    @Size(min = 6, max = 254)
    @NotEmpty
    @Email
    private String email;
}
