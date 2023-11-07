package ru.practicum.explorewithme.service.event;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.in.create.NewEventDto;
import ru.practicum.explorewithme.dto.out.CategoryDto;
import ru.practicum.explorewithme.dto.out.EventFullDto;
import ru.practicum.explorewithme.dto.out.outshort.LocationDto;
import ru.practicum.explorewithme.dto.out.outshort.UserShortDto;
import ru.practicum.explorewithme.model.Event;

import static ru.practicum.dto.ContextStats.formatter;

@Component
public class EventMapper {

    public EventFullDto toFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(),event.getCategory().getName()))
                .createdOn(formatter.format(event.getCreatedOn()))
                .description(event.getDescription())
                .eventDate(formatter.format(event.getEventDate()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .state(event.getState())
                .location(new LocationDto(event.getLocation().getLat(), event.getLocation().getLon()))
                .build();
    }

    public Event fromDto(NewEventDto newEventDto) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .description(newEventDto.getDescription())
                .annotation(newEventDto.getAnnotation())
                .paid(newEventDto.getPaid())
                .eventDate(newEventDto.getEventDate())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .build();
    }
}
