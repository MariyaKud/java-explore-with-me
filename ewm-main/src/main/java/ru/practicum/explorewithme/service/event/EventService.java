package ru.practicum.explorewithme.service.event;

import ru.practicum.explorewithme.dto.in.create.NewEventDto;
import ru.practicum.explorewithme.dto.out.EventFullDto;

public interface EventService {
    EventFullDto createEvent(Long userId, NewEventDto newEventDto);
}
