package ru.practicum.explorewithme.exeption;

import java.util.Set;

public class NotFoundListException extends RuntimeException {
    public NotFoundListException(Set<Long> ids, Class c) {
        super(String.format("%s id in [%s] contain no found element.", c.getName(), ids));
    }
}
