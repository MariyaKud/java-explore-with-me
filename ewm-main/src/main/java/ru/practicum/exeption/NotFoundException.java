package ru.practicum.exeption;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id, Class c) {
        super(String.format("%s with id = %s not found.", c.getName(), id));
    }
}
