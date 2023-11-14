package ru.practicum.validation;

import ru.practicum.dto.input.create.NewEventDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class CheckDateValidator implements ConstraintValidator<DateAfterTwoHourFromNow, NewEventDto> {
    @Override
    public void initialize(DateAfterTwoHourFromNow constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(NewEventDto newEventDto, ConstraintValidatorContext constraintValidatorContext) {
        if (newEventDto.getEventDate() == null) {
            return false;
        }
        return newEventDto.getEventDate().plusHours(2).isAfter(LocalDateTime.now());
    }
}
