package ru.practicum.explorewithme.exeption;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id, Class c) {
        super(String.format("%s с id = %s не найден.", c.getName(), id));
    }
}
