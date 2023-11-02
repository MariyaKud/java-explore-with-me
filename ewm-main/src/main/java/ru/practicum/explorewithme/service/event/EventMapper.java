package ru.practicum.explorewithme.service.event;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.in.create.NewEventDto;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.model.Event;

@Component
public class EventMapper {

    public EventFullDto toFullDto(Event event) {
        return EventFullDto.builder().build();
    }

    public Event fromDto(NewEventDto newEventDto) {
        return null;

    }
}
