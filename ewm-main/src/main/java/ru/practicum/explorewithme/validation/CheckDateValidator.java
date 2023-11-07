package ru.practicum.explorewithme.validation;

import ru.practicum.explorewithme.dto.in.create.NewEventDto;

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
        return newEventDto.getEventDate().plusHours(2).isAfter(LocalDateTime.now());
    }
}
